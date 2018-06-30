// �ж��Ƿ��Ƕ�������
function isObj(object) {
	return object && typeof (object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
}
// �ж��º�������
function isArray(object) {
	return object && typeof (object) == 'object' && object.constructor == Array;
}
// ��ȡ����ĳ���
function getLength(object) {
	var count = 0;
	for (var i in object) count++;
	return count;
}

// ==========================
// �ж����������Ƿ����
// ==========================	
function Compare(objA, objB) {
	if (!isObj(objA) || !isObj(objB)) { //�ж������Ƿ��Ƕ���
		if( objA==objB ){ //���Ƕ������;��ж��ַ������������Ƿ����
				return true;
			} else{
				return false;
			}
	} else if(getLength(objA) != getLength(objB)) {
		return false; //�жϳ����Ƿ�һ��
	} else {
		return CompareObj(objA, objB, true);//Ĭ��Ϊtrue		
	}
}
function CompareObj(objA, objB, flag) {
	for (var key in objA) {
		if (!flag) //��������ѭ��
		break;
		if (!objB.hasOwnProperty(key)) {
			flag = false;
			break;
		}
		if (!isArray(objA[key])) { //�Ӽ���������ʱ,�Ƚ�����ֵ
			if (objB[key] != objA[key]) { flag = false; break; }
		} else {
			if (!isArray(objB[key])) { flag = false; break; }
			var oA = objA[key], oB = objB[key];
			if (oA.length != oB.length) { flag = false; break; }
			for (var k in oA) {
				if (!flag) //��������ѭ����Ϊ�˲��õݹ����
				break;
				flag = CompareObj(oA[k], oB[k], flag);
			}
		}
	}
	return flag;
}

function valueOk(element) {
	if( element != '' && element.length >=6 && element.length <=12 ) {
		return true;
	} else {
		return false;
	}
}