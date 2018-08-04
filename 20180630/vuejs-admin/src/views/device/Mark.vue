<template>

  <el-container>
    <el-aside width="910px">
      <canvas width="900" height="600" id="canvas" class="lamp-canvas" @mousedown="mousedown" @mouseup="mouseup"></canvas>
    </el-aside>
    <el-container width="200px">
      <el-header style="height:auto;">
        <el-popover placement="bottom-start" width="400" trigger="click" v-model="visible">
          <el-button slot="reference" @click="addPoints">添加</el-button>
          <el-container width="180px">
            <el-container width="180px">
              <el-aside width="50%">
                <el-form size="mini" label-width="60px" :label-position="labelPosition" ref="form" style="padding-top:10px;">
                  <el-form-item label="维修人">
                    <el-input v-model="form.name" placeholder="请输入维修人" style="min-width:120px;max-width:140px"></el-input>
                  </el-form-item>
                  <el-form-item label="类型">
                    <el-select v-model="form.type" placeholder="请选择" style="min-width:120px;max-width:140px">
                      <el-option v-for="_item in types" :key="_item.value" :label="_item.label" :value="_item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="序列号">
                    <el-input v-model="form.sn" placeholder="请输入序列号" style="min-width:120px;max-width:140px"></el-input>
                  </el-form-item>
                  <el-form-item label="备注">
                    <el-input style="min-width:120px;max-width:140px" v-model="form.remark" type="textarea" :autosize="{minRows: 1, maxRows: 2}" placeholder="请输入备注"></el-input>
                  </el-form-item>
                </el-form>
              </el-aside>
              <el-main width="50%">
                <el-form size="mini" ref="form" label-width="60px" style="padding-top:10px;">
                  <el-form-item v-for="(point,_index) in form.points" :key="point.id" :label="(_index ==0 ?'左上':(_index ==1 ?'右上': (_index ==2 ?'右下':(_index ==3 ?'左上':'' ) )) ) " style="padding-left:6px;">
                    <el-input v-if="_index == 0" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+point.x+',y:'+point.y+')'"></el-input>
                    <el-input v-if="_index == 1" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+point.x+',y:'+point.y+')'"></el-input>
                    <el-input v-if="_index == 2" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+point.x+',y:'+point.y+')'"></el-input>
                    <el-input v-if="_index == 3" style="min-width:120px;max-width:140px" :disabled="true" :value="'(x:'+point.x+',y:'+point.y+')'"></el-input>
                  </el-form-item>
                </el-form>
              </el-main>
            </el-container>
            <el-footer width="180px" style="text-align: center;">
              <el-form width="100%">
                <el-form-item size="mini">
                  <el-button type="primary" @click="submitPoints">确定</el-button>
                  <el-button @click="visible = false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-footer>
          </el-container>
        </el-popover>
      </el-header>
      <el-main width="100%">
        <el-collapse v-for="item in lamps" :key="item.id">
          <el-collapse-item :title="item.id+'测试'" :name="item.id+'测试'">
            <el-row v-for="(point,_index) in item.points" :key="point.id">
              <el-col :span="24">
                <span v-if="_index == 0">左上</span>
                <span v-if="_index ==1">右上</span>
                <span v-if="_index ==2">右下</span>
                <span v-if="_index ==3">左下</span>
                <span>(x:{{point.x}},y:{{point.y}})</span>
              </el-col>
            </el-row>
          </el-collapse-item>
        </el-collapse>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import {
  getDeviceDetail,
  updateDeviceInfo,
  addDevice
} from "../../http/device";
import { getLampList } from "../../http/lamp";
import { mapState } from "vuex";
export default {
  name: "DeviceMark",
  mounted() {
    // 加载设备信息
    if (!this.$route.params.devId) return;
    this.initDraw();
  },
  data() {
    return {
      lamps: [{}],
      labelPosition: "left",
      visible: true,
      canvas: {
        width: 900,
        height: 600
      },
      form: {
        maintainer: "",
        sn: "",
        remark: "",
        type: "",
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
      types: [
        {
          value: "选项1",
          label: "黄金糕"
        },
        {
          value: "选项2",
          label: "双皮奶"
        },
        {
          value: "选项3",
          label: "蚵仔煎"
        },
        {
          value: "选项4",
          label: "龙须面"
        },
        {
          value: "选项5",
          label: "北京烤鸭"
        }
      ],
      deviceId: this.$route.params.devId,
      sourceI: -1,
      sourceJ: -1,
      addPonitsJ: -1,
      rangs: {
        start: {
          x: -1,
          y: -1
        },
        end: {
          x: -1,
          y: -1
        }
      },
      rules: {}
    };
  },
  computed: {
    ...mapState["user"]
  },
  methods: {
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
      for (var i = 0; i < _lamps.length; i++) {
        points = _lamps[i].points;
        for (var j = 0; j < points.length; j++) {
          console.log(
            "i:" + i + "{x:" + points[j].x + ",y:" + points[j].y + "}"
          );
          cxt.beginPath();
          cxt.fillStyle = "blue";
          cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
          cxt.fill();
          cxt.strokeStyle = "white"; //线条的颜色
          if (j == 0) {
            cxt.fillStyle = "#e81445";
            cxt.font = "16px Georgia";
            cxt.fillText(_lamps[i].id, points[1].x, points[1].y - 4);
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
      const form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        var points = form.points;
        console.log("i:" + i + "{x:" + points[j].x + ",y:" + points[j].y + "}");
        cxt.beginPath();
        cxt.fillStyle = "blue";
        cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
        cxt.fill();
        cxt.strokeStyle = "white"; //线条的颜色
        if (j == 0) {
          cxt.fillStyle = "#e81445";
          cxt.font = "16px Georgia";
          cxt.fillText(form.name, points[1].x, points[1].y - 4);
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
    mousedown(event) {
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      var x = event.clientX - canvas.getBoundingClientRect().left;
      var y = event.clientY - canvas.getBoundingClientRect().top;
      const _lamps = this.lamps;
      var points;
      this.rangs.start.x = x;
      this.rangs.start.y = y;
      this.rangs.end.x = x;
      this.rangs.end.y = y;
      for (var i = 0; i < _lamps.length; i++) {
        points = _lamps[i].points;
        for (var j = 0; j < points.length; j++) {
          cxt.beginPath();

          cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
          if (cxt.isPointInPath(x, y)) {
            this.sourceI = i;
            this.sourceJ = j;
            break;
          }
        }
      }
      const form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        cxt.beginPath();

        cxt.arc(form.points[j].x, form.points[j].y, 3, 0, Math.PI * 2);
        if (cxt.isPointInPath(x, y)) {
          this.addPonitsJ = j;
          break;
        }
      }
    },
    mouseup(event) {
      var canvas = document.getElementById("canvas");
      var cxt = canvas.getContext("2d");
      var x = event.clientX - canvas.getBoundingClientRect().left;
      var y = event.clientY - canvas.getBoundingClientRect().top;
      var points;
      const _lamps = this.lamps;
      for (var i = 0; i < _lamps.length; i++) {
        if (this.sourceI == i) {
          points = _lamps[i].points;
          for (var j = 0; j < points.length; j++) {
            if (this.sourceJ == j) {
              points[j].x = x;
              points[j].y = y;
              this.sourceI = -1;
              this.sourceJ = -1;
              cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
              this.draw();
            }
          }
        }
      }
      const form = this.form;
      for (var j = 0; j < form.points.length; j++) {
        if (this.addPonitsJ == j) {
          form.points[j].x = x;
          form.points[j].y = y;
          cxt.clearRect(0, 0, this.canvas.width, this.canvas.height);
          this.draw();
          this.addPonitsJ = -1;
        }
      }
      const rangs = this.rangs;
      if (rangs.start.x != -1) {
        rangs.end.x = x;
        rangs.end.y = y;
        // cxt.strokeStyle="#FF0000";
        //cxt.strokeRect( rangs.start.x, rangs.start.y,rangs.end.x-rangs.start.x,
        //rangs.end.y-rangs.start.y,"#FF0000");
        // cxt.stroke();
        //判断区域
        var minX = rangs.start.x;
        var minY = rangs.start.y;
        var maxX = rangs.end.x;
        var maxY = rangs.end.y;
        if (minX > rangs.end.x) {
          minX = rangs.end.x;
        }
        if (maxX < rangs.start.x) {
          maxX = rangs.start.x;
        }
        if (minY > rangs.end.y) {
          minY = rangs.end.y;
        }
        if (maxY < rangs.start.y) {
          maxY = rangs.start.y;
        }
        return;
        for (var m = 0; m < form.points.length; i++) {
          const tempX = form.points[m].x;
          const tempY = form.points[m].y;
          if (tempX > minX && tempX < maxX && tempY > minY && tempY < maxY) {
            console.log(this.form.points[m]);
            //this.form.points[m].isSelect = false;
          }
        }
      }
    },
    initDraw() {
      var _this = this;
      getLampList({ deviceId: this.deviceId })
        .then(res => {
          _this.lamps = res.data;
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
      const _form = this.form;
      var points = _form.points;
      for (var j = 0; j < points.length; j++) {
        cxt.beginPath();
        if (points[j].isSelect) {
          cxt.fillStyle = "red";
        } else {
          cxt.fillStyle = "blue";
        }
        cxt.arc(points[j].x, points[j].y, 3, 0, Math.PI * 2);
        cxt.fill();
        cxt.strokeStyle = "white"; //线条的颜色
        if (j == 0) {
          cxt.fillStyle = "#e81445";
          cxt.font = "16px Georgia";
          cxt.fillText(_form.name, points[1].x, points[1].y - 4);
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
    submitPoints() {},
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
                console.log(err);
                this.$message.error(err.message);
              });
          } else {
            // 新增保存
            addDevice(this.device)
              .then(res => {
                this.$message({
                  type: "success",
                  message: "新增成功!"
                });
                this.$router.back();
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
    }
  }
};
</script>

<style scoped>
.lamp-canvas {
  background-image: url("../../assets/demo_picture.jpg");
  background-size: cover;
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
</style>
