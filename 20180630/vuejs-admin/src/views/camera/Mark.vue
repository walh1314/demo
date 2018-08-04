<template>

  <el-container>
    <el-aside :style="canvasParentStyle">
      <canvas id="canvas" :style="canvasStyle" class="lamp-canvas" @mousedown="mousedown" @mouseup="mouseup"></canvas>
      <img id="img" :src="backUrl" style="display:none;" @error="errorImage" @load="imageLoaded"/>
    </el-aside>
    <el-container width="210px">
      <el-header >
        <el-row type="flex">
          <el-col :span="24">
            <el-popover placement="bottom-start" trigger="click" v-model="visible">
              <el-button slot="reference" @click="addPoints">添加</el-button>
              <el-container width="320px">
                <el-container width="320px" style="display:flex">
                  <el-aside width="50%" style="overflow:hidden;">
                    <el-form  size="mini" label-width="60px" :label-position="labelPosition" :model="form" ref="form" style="padding-top:10px;">
                      <el-form-item label="维修人" prop="maintainer">
                        <el-input v-model="form.maintainer" placeholder="请输入维修人" style="min-width:120px;max-width:140px"></el-input>
                      </el-form-item>
                       <el-form-item label="临界值">
                        <template>
                          <el-row :gutter="24" style="inline">
                            <el-col :span="7"><input v-model="form.threshold[0]" class="el-custom-input-form" type="number"  min="0" max="255"/></el-col>
                            <el-col :span="7"><input v-model="form.threshold[1]" class="el-custom-input-form" type="number"  min="0" max="255"/></el-col>
                            <el-col :span="7"><input v-model="form.threshold[2]" class="el-custom-input-form" type="number"  min="0" max="255"/></el-col>
                          </el-row>
                        </template>
                      </el-form-item>
                      <el-form-item label="类型" prop="type">
                        <el-select v-model="form.type" placeholder="请选择" style="min-width:120px;max-width:140px">
                          <el-option v-for="_item in types" :key="_item.id" :label="_item.name" :value="_item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="序列号" prop="sn">
                        <el-input v-model="form.sn" @input="changSn" @change="changSn" placeholder="请输入序列号" style="min-width:120px;max-width:140px"></el-input>
                      </el-form-item>
                      <el-form-item label="备注" prop="desc">
                        <el-input style="min-width:120px;max-width:140px" v-model="form.desc" type="textarea" :autosize="{minRows: 1, maxRows: 2}" placeholder="请输入备注"></el-input>
                      </el-form-item>
                    </el-form>
                  </el-aside>
                  <el-main width="50%" style="overflow:hidden;">
                    <el-form size="mini" width="160px" ref="form" label-width="60px" style="padding-top:10px;">
                      <el-form-item v-for="(point,_index) in form.points" :key="point.id" :label="(_index ==0 ?'左上':(_index ==1 ?'右上': (_index ==2 ?'右下':(_index ==3 ?'左上':'' ) )) ) " style="padding-left:4px;">
                        <el-input v-if="_index == 0" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                        <el-input v-if="_index == 1" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                        <el-input v-if="_index == 2" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                        <el-input v-if="_index == 3" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                      </el-form-item>
                    </el-form>
                  </el-main>
                </el-container>
                <el-footer width="180px" style="text-align: center;">
                  <el-form width="100%">
                    <el-form-item size="mini">
                      <el-button type="primary" @click="submitPoints('form')">确定</el-button>
                      <el-button @click="cancelPoint">取消</el-button>
                    </el-form-item>
                  </el-form>
                </el-footer>
              </el-container>
            </el-popover>
          </el-col>
          <el-col :span="24">
            <el-button  @click="getImage">获取图像</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main width="100%" style="overflow:auto;">
        <el-collapse v-for="item in lamps" :key="item.id">
          <el-collapse-item  width="90%">
            <template slot="title">
              <i class="header-icon el-icon-close" title="删除" style="float: left;line-height: 48px;font-size:20px; margin-left:10px;" @click.stop="deleteLamp(item.id)"></i>
              <span>{{item.sn}}</span>
            </template>
            <el-form  width="95%" size="mini" label-width="60px" style="padding:0px;margin-left:30px;margin-right:20px;width:200px;" label-position="center">
              <el-form-item label="维修人">
                <el-input v-model="item.maintainer" placeholder="请输入维修人" style="min-width:80px;max-width:110px"></el-input>
              </el-form-item>
              <el-form-item label="临界值">
                <template>
                  <el-row :gutter="20" style="inline">
                    <el-col :span="6"><input v-model="item.threshold[0]" class="el-custom-input" type="number"  min="0" max="255"/></el-col>
                    <el-col :span="6"><input v-model="item.threshold[1]" class="el-custom-input" type="number"  min="0" max="255"/></el-col>
                    <el-col :span="6"><input v-model="item.threshold[2]" class="el-custom-input" type="number"  min="0" max="255"/></el-col>
                  </el-row>
                </template>
              </el-form-item>
              <el-form-item width="95px" v-for="(point,_index) in item.points" :key="point.id" :label="(_index ==0 ?'左上':(_index ==1 ?'右上': (_index ==2 ?'右下':(_index ==3 ?'左上':'' ) )) ) " style="padding-left:0px;">
                <el-input v-if="_index == 0" style="min-width:80px;max-width:110px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                <el-input v-if="_index == 1" style="min-width:80px;max-width:110px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                <el-input v-if="_index == 2" style="min-width:80px;max-width:110px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
                <el-input v-if="_index == 3" style="min-width:80px;max-width:110px" :disabled="true" :value="'(x:'+Math.round(point.x/scale)+',y:'+Math.round(point.y/scale)+')'"></el-input>
              </el-form-item>
              <el-form-item style="padding:0px;margin-left:-60px;margin-right:0px;">
                <el-button type="primary" @click="submitLamp(item)">确定</el-button>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import {
  getMarkLamps,
  addCameraMarkLamp,
  delCameraMarkLamp,
  updateCameraMarkLamp,
  getMarkImage
} from "../../http/camera";
import { getLampTypeList } from "../../http/lamp";
import { mapState } from "vuex";
export default {
  name: "CameraMark",
  mounted() {
    // 加载设备信息
    getLampTypeList()
      .then(res => {
        this.types = res.data;
      })
      .catch(err => {
        this.$message.error(err.message);
      });
    if (!this.$route.params.deviceId) return;
    this.getImage();
  },
  created: function() {
    var _this = this;
    window.onkeydown = function(e) {
      _this.ctrlKey = e.ctrlKey;
    };
    window.onkeyup = function(e) {
      _this.ctrlKey = e.ctrlKey;
    };
  },
  data() {
    return {
      ctrlKey: false,
      lamps: [],
      backUrl:"",
      labelPosition: "left",
      canvasParentStyle:{
        width:"896px",
        height:"504px",
      },
      visible: false,
      canvas: {
        width: 896,
        height: 504
      },
      initForm: {
        maintainer: "",
        sn: "序列号",
        desc: "",
        type: "",
        isShow: false,
        threshold:[],
        points: [
          {
            x: 20,
            y: 20,
            isSelect: true
          },
          {
            x: 40,
            y: 20,
            isSelect: true
          },
          {
            x: 40,
            y: 60,
            isSelect: true
          },
          {
            x: 20,
            y: 60,
            isSelect: true
          }
        ]
      },
      form: { points: [],threshold:[] },
      types: [],
      deviceId: this.$route.params.deviceId,
      sourceI: -1,
      sourceJ: -1,
      max_width:896,
      max_height:504,
      scale:0.7,
      addPonitsJ: -1,
      sourceX: null,
      sourceY: null,
      rules: {
        sn: [
          { required: true, message: "请输入序列号", trigger: "blur,change" }
        ]
      }
    };
  },
  computed: {
    ...mapState["user"],
    canvasStyle: function() {
        const result = {};
        result["background-image"] = "url("+this.backUrl+")";
        return result;
    }
  },
  methods: {
    errorImage:function(event){
        
    },
    getImage:function(){
      getMarkImage({ deviceId: this.deviceId }).then(res => {
        //console.log(res);
          if(res.code ==1){
            this.backUrl = res.data;
          } else {
            this.backUrl = "";
          }
          })
          .catch(err => {
            this.$message.error(err.message);
          });
    },
    imageLoaded:function(event){
        const img = document.getElementById('img');
        const canvas = document.getElementById("canvas");
        const cxt = canvas.getContext("2d");

        const preWidth= this.max_width/img.naturalWidth;
        const preHeight= this.max_height/img.naturalHeight;
        var pre = Math.min(preWidth,preHeight);
        pre =( pre > 1 ? 1:pre);
        //console.log(pre);
        canvas.width=Math.round(img.naturalWidth * pre);
        canvas.height=Math.round(img.naturalHeight * pre);
        this.scale = pre;
        // console.log(canvas.width);
         //console.log(canvas.height);
        //canvas.width=img.naturalWidth;
        //canvas.height=img.naturalHeight;
        this.canvasParentStyle["width"]=null;
        this.canvasParentStyle["height"]=null;
        // var src = canvas.toDataURL('image/jpg');
        //初始化数据比列
        this.initDraw();
    },
    submitLamp(lamp) {
      var _this = this;
      lamp.scale = this.scale;//存入尺寸
      updateCameraMarkLamp(lamp)
        .then(res => {
          this.$message.success(res.msg || "成功");
        })
        .catch(err => {
          console.log(err);
          this.$message.error(err.message);
        });
    },
    draw() {
      const canvas = document.getElementById("canvas");
      const cxt = canvas.getContext("2d");
      cxt.fillStyle = "blue";
      const _lamps = this.lamps;
      cxt.setLineDash([4, 4]);
      if (_lamps != null && _lamps != undefined && typeof _lamps != "object") {
        return;
      }
      var points;
      if (_lamps != null && _lamps != undefined) {
        for (var i = 0; i < _lamps.length; i++) {
          points = _lamps[i].points;
          for (var j = 0; j < points.length; j++) {
            cxt.beginPath();
            cxt.fillStyle = "blue";
            cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
            cxt.fill();
            cxt.strokeStyle = "white"; //线条的颜色
            if (points[j].isSelect) {
              cxt.beginPath();
              cxt.fillStyle = "#00FFFF";
              cxt.fillRect(points[j].x - 3, points[j].y - 3, 8, 8);
            }
            if (j == 0) {
              cxt.fillStyle = "#e81445";
              cxt.font = "16px Georgia";
              cxt.fillText(_lamps[i].sn, points[1].x, points[1].y - 4);
              cxt.stroke();
            } else if (j < points.length - 1) {
              cxt.moveTo(points[j - 1].x, points[j - 1].y);
              cxt.lineTo(points[j].x, points[j].y);
              cxt.stroke();
            } else if (j == points.length - 1) {
              cxt.moveTo(points[j - 1].x, points[j - 1].y);
              cxt.lineTo(points[j].x, points[j].y);
              cxt.lineTo(points[0].x, points[0].y);
              cxt.stroke();
            }
          }
        }
      }
      const form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        var points = form.points;
        cxt.beginPath();
        cxt.fillStyle = "blue";
        cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
        cxt.fill();
        cxt.strokeStyle = "white"; //线条的颜色
        if (points[j].isSelect) {
          cxt.beginPath();
          cxt.fillStyle = "#00FFFF";
          cxt.fillRect(points[j].x - 3, points[j].y - 3, 8, 8);
        }
        if (j == 0) {
          cxt.fillStyle = "#e81445";
          cxt.font = "16px Georgia";
          cxt.fillText(form.sn, points[1].x, points[1].y - 4);
          cxt.stroke();
        } else if (j < points.length - 1) {
          cxt.moveTo(points[j - 1].x, points[j - 1].y);
          cxt.lineTo(points[j].x, points[j].y);
          cxt.stroke();
        } else if (j == points.length - 1) {
          cxt.moveTo(points[j - 1].x, points[j - 1].y);
          cxt.lineTo(points[j].x, points[j].y);
          cxt.lineTo(points[0].x, points[0].y);
          cxt.stroke();
        }
      }
    },
    cancelPoint() {
      var _this = this;
      //debugger;
      _this.visible = !_this.visible;
      _this.form = { points: [],threshold: [] };
      this.initForm.isShow = false;
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
      //重画
      this.draw();
    },
    deleteLamp(id) {
      const _this = this;
      this.$confirm("此操作将永久删除该标示, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }) .then(() => {
          delCameraMarkLamp(id)
            .then(res => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              var canvas = document.getElementById("canvas");
              var cxt = canvas.getContext("2d");
              cxt.clearRect(0, 0, _this.canvas.width, _this.canvas.height);
              _this.initDraw();
            })
            .catch(err => {
              this.$message.error(err.message);
            });
        })
        .catch(err => {
          console.log(err);
        });
    },
    mousedown(event) {
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      var x = event.clientX - canvas.getBoundingClientRect().left;
      var y = event.clientY - canvas.getBoundingClientRect().top;
      const _lamps = this.lamps;
      var points;

      this.sourceX = null;
      this.sourceY = null;
      this.sourceI = -1;
      this.sourceJ = -1;
      this.addPonitsJ = -1;
      var isContaint = false;
      if (_lamps != null && _lamps != undefined) {
        for (var i = 0; i < _lamps.length; i++) {
          if (isContaint) {
            break;
          }
          points = _lamps[i].points;

          for (var j = 0; j < points.length; j++) {
            if (points[j].isSelect) {
              cxt.beginPath();
              cxt.fillStyle = "#00FFFF";
              cxt.fillRect(points[j].x - 3, points[j].y - 3, 8, 8);
            }
            cxt.beginPath();
            cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
            if (cxt.isPointInPath(x, y)) {
              this.sourceI = i;
              this.sourceJ = j;
              this.sourceX = x;
              this.sourceY = y;
              isContaint = true;
              break;
            } else if (!isContaint) {
              this.sourceI = -1;
              this.sourceJ = -1;
            }
          }
        }
      }
      const form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        cxt.beginPath();
        if (form.points[j].isSelect) {
          cxt.beginPath();
          cxt.fillStyle = "#00FFFF";
          cxt.fillRect(form.points[j].x - 3, form.points[j].y - 3, 8, 8);
        }
        cxt.arc(form.points[j].x, form.points[j].y, 3, 0, Math.PI * 2);
        if (cxt.isPointInPath(x, y)) {
          this.addPonitsJ = j;
          this.sourceX = x;
          this.sourceY = y;
          break;
        } else {
          this.addPonitsJ = -1;
        }
      }
    },
    mouseup(event) {
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      var x = event.clientX - canvas.getBoundingClientRect().left;
      var y = event.clientY - canvas.getBoundingClientRect().top;
      var points;
      var _lamps = this.lamps;
      const juliX = this.sourceX == null ? 0 : this.sourceX - x;
      const juliY = this.sourceY == null ? 0 : this.sourceY - y;
      for (var i = 0; i < _lamps.length; i++) {
        points = _lamps[i].points;
        for (var j = 0; j < points.length; j++) {
          if (this.sourceJ == j && this.sourceI == i) {
            points[j].x = points[j].x - juliX;
            points[j].y = points[j].y - juliY;
            if (juliX == 0 && juliY == 0 && this.ctrlKey) {
              points[j].isSelect = !points[j].isSelect;
            }
          } else if (
            points[j].isSelect &&
            this.sourceJ != -1 &&
            points[this.sourceJ].isSelect
          ) {
            points[j].x = points[j].x - juliX;
            points[j].y = points[j].y - juliY;
          }
          if (
            this.sourceX == null &&
            this.addPonitsJ == -1 &&
            this.sourceI == -1
          ) {
            points[j].isSelect = false;
          }
        }
      }
      var form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        if (this.addPonitsJ == j) {
          form.points[j].x = form.points[j].x - juliX;
          form.points[j].y = form.points[j].y - juliY;
          if (juliX == 0 && juliY == 0 && this.ctrlKey) {
            form.points[j].isSelect = !form.points[j].isSelect;
          }
        } else if (
          form.points[j].isSelect &&
          this.addPonitsJ != -1 &&
          form.points[this.addPonitsJ].isSelect
        ) {
          form.points[j].x = form.points[j].x - juliX;
          form.points[j].y = form.points[j].y - juliY;
        }
        if (
          this.sourceX == null &&
          this.addPonitsJ == -1 &&
          this.sourceI == -1
        ) {
          form.points[j].isSelect = false;
        }
      }
      //重新规划上下左右数据;
      cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
      //重画
      this.draw();
    },
    changSn() {
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
      //重画
      this.draw();
    },

    initDraw() {
      var _this = this;
      getMarkLamps({deviceId: this.deviceId,scale:this.scale})
        .then(res => {
         // debugger;
          _this.lamps = res.data == undefined ? []:res.data ;
          //根据获得数据
          this.draw();
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
    addPoints() {
      const canvas = document.getElementById("canvas");
      const cxt = canvas.getContext("2d");
      cxt.fillStyle = "blue";
      cxt.setLineDash([4, 4]);
      this.form =
        this.form.isShow == undefined
          ? JSON.parse(JSON.stringify(this.initForm))
          : this.form;
      var _form = this.form;
      var points = _form.points;
      if (_form.isShow) {
        return;
      }
      _form.isShow = true;
      this.initForm.isShow = true;
      for (var j = 0; j < points.length; j++) {
        cxt.beginPath();
        cxt.fillStyle = "blue";
        if (points[j].isSelect) {
          cxt.beginPath();
          cxt.fillStyle = "#00FFFF";
          cxt.fillRect(points[j].x - 3, points[j].y - 3, 8, 8);
        }
        cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
        cxt.fill();
        cxt.strokeStyle = "white"; //线条的颜色
        if (j == 0) {
          cxt.fillStyle = "#e81445";
          cxt.font = "16px Georgia";
          cxt.fillText(_form.sn, points[1].x, points[1].y - 4);
          cxt.stroke();
        } else if (j < points.length - 1) {
          cxt.moveTo(points[j - 1].x, points[j - 1].y);
          cxt.lineTo(points[j].x, points[j].y);
          cxt.stroke();
        } else if (j == points.length - 1) {
          cxt.moveTo(points[j - 1].x, points[j - 1].y);
          cxt.lineTo(points[j].x, points[j].y);
          cxt.lineTo(points[0].x, points[0].y);
          cxt.stroke();
        }
      }
    },
    submitPoints: function(formName) {
      var _this = this;
      this.form.deviceId = this.deviceId;
      this.form.scale = this.scale;
      addCameraMarkLamp(this.form)
        .then(res => {
          _this.visible = false;
          if (res.code == 1) {
            _this.form.id = res.data.lampInfo.id;
          } else {
            this.$message.error(res.msg || "系统异常");
            return;
          }
          _this.lamps.push(_this.form);
          var canvas = document.getElementById("canvas");
          var cxt = canvas.getContext("2d");
          cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
          //重画
          this.draw();
          this.form.initForm = false;
          _this.form = JSON.parse(JSON.stringify(this.initForm));
        })
        .catch(err => {
          console.log(err);
          this.$message.error(err.message);
        });
    },
    back() {
      this.$router.back();
    },
    handleAvatarSuccess(res, file) {
      this.device.pic = "/" + res.url;
    },
    saveDeviceMark(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.device.code += "";
          if (this.device.id) {
            // 修改保存
            updateDeviceInfo(this.device)
              .then(res => {
                this.$router.back();
              })
              .catch(err => {
                this.$message.error(err.message);
              });
          } else {
            // 新增保存
            addDevice(this.device)
              .then(res => {
                this.listenerResult();
                //this.$router.back();
              })
              .catch(err => {
                this.$message.error(err.message);
              });
          }
        }
      });
    },
    removeDevArg(devArg, index) {
      this.device.args.splice(index, 1);
    },
    addDevArg() {
      this.device.args.push({});
    },
    clearForm(form) {
      this.device.args = this.device.args.map(item => {
        item.desc = "";
        return item;
      });
      this.$refs[form].resetFields();
   
   },
  listenerResult(){//
      const protocolStr = "http:";
      let  protocolStart = null;
      if(protocolStr == "https:"){
            protocolStart= "https";
        } else {
            protocolStart= "http";
      }
      const PORT = 8000;
      const CONTEXT_PATH = "/lamp";
      const IP = "127.0.0.1";
      const WEBSOCKET_SERVICE = protocolStart+ "://"+IP+":"+PORT+CONTEXT_PATH+"/websocket";
      const websocketClient  = this.$Stompjs.over(new this.$SockJS(WEBSOCKET_SERVICE));
      let that = this;
     // debugger;
      let result = {"code":"0","msg":"fail"};
      let isReturn = false;
      websocketClient.connect({'name':that.$route.params.topic}, function (frame) {
        websocketClient.subscribe('/user/topic/'+that.$route.params.topic, function (sockeResult) {
            //console.log(greeting);
            isReturn = true;
            try{
              if(sockeResult != null ){
                result = JSON.parse(greeting.body)
              }
            } catch (e){
              //result = sockeResult;
            }
        });
      });
     var promise  =  new Promise(function(resolve, reject) {
          let count = 0;
          const maxCount = 100;
          let interval = setInterval(function(){
              //count++;
              if(count++ > 100){
                reject(result);
                clearInterval(interval);
              } else if(isReturn){
                resolve(result);
              }
          }, 100);
      });
      promise.then(result => {
          console.log(result);
          if(websocketClient != null){
            try{
            websocketClient.disconnect();
            }catch(e){

            }
          }
          if(result.code ==1){
                this.$message({
                  type: "success",
                  message: "新增成功!"
                });
          } else {
            this.$message.error(err.message);
          }
      }).catch(result => {
        console.log("shibai==================");
        console.log(result);
        if(websocketClient != null){
          try{
          websocketClient.disconnect();
          }catch(e){

          }
        }
      });
    },
    listenerWaitResult: function(resolve, reject) {
          var timeOut = Math.random() * 2;
          console.log('set timeout to: ' + timeOut + ' seconds.');
          setTimeout(function () {
              if (timeOut < 1) {
                  console.log('call resolve()...');
                  resolve('200 OK');
              } else {
                  console.log('call reject()...');
                  reject('timeout in ' + timeOut + ' seconds.');
              }
          }, timeOut * 1000);
    },
  }
};
</script>

<style scoped>
.lamp-canvas {
  background-image: url("../../assets/demo_picture.jpg");
  background-size: contain;
  background-repeat: no-repeat;
  background-color: rgb(238, 238, 238);
  border: 1px solid rgb(168, 68, 68);
  cursor: default;
}
.form {
  width: 52%;
  /* margin: 0 auto; */
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.el-main {
  color: #333;
  padding: 0px;
  text-align: center;
  line-height: 160px;
}
.el-header {
  background-color: #fafafa;
  color: #333;
  padding: 0px;
  text-align: center;
  line-height: 16px;
}
.el-custom-input{
    height: 28px;
    min-width: 28px;
    max-width: 28px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    line-height: 28px;
    margin-left: 4px;
    padding-left: 6px;
}

.el-custom-input-form{
    height: 28px;
    min-width: 28px;
    max-width: 28px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    line-height: 28px;
    margin-left: -13px;
    padding-left: 6px;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
}
 
input[type="number"] {
    -moz-appearance: textfield;
}

</style>
