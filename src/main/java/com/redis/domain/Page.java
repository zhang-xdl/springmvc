package com.redis.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Page implements Serializable{

	private static final long serialVersionUID = 4986247063453908308L;
	private int showCount; //ÿҳ��ʾ��¼��
	private int totalPage;		//��ҳ��
	private int totalResult;	//�ܼ�¼��
	private int currentPage;	//��ǰҳ
	private int currentResult;	//��ǰ��¼��ʼ����
	private boolean entityOrField;	//true:��Ҫ��ҳ�ĵط�������Ĳ������Pageʵ�壻false:��Ҫ��ҳ�ĵط�������Ĳ��������ʵ��ӵ��Page����
	private String pageStr;		//����ҳ����ʾ�ĵײ���ҳ��������ϸ��getPageStr();
	//	private PageData pd = new PageData();
	private int prePage;//��һҳ
	private int nextPage;//��һҳ
	private boolean hasPre;//�Ƿ�����һҳ
	private boolean hasNext;//�Ƿ�����һҳ
	private List result;//���ؽ��
	private List<String> childOrganIds = new ArrayList<String>();
	private List<String> companyStates = new ArrayList<String>();
	private List<String> companyTypes = new ArrayList<String>();

	//	public Page(){
//		try {
//			this.showCount = Integer.parseInt(Tools.readTxtFile(Const.PAGE));
//		} catch (Exception e) {
//			this.showCount = 15;
//		}
//	}
	public Page(int currentPage, int showCount) {
		super();
		this.currentPage = (currentPage<1)?1:currentPage;
		this.showCount = showCount;
		//�Ƿ�����һҳ
		this.hasPre = (currentPage==1)?false:true;
		//�Ƿ�����һҳ
		this.hasNext = (currentPage==totalPage)?false:true;
		//��һҳ
		if(hasPre){
			this.prePage = (currentPage-1);
		}
		//��һҳ
		if(hasNext){
			this.nextPage = currentPage+1;
		}

	}
	public Page(int currentPage, int showCount, int totalResult) {
		super();
		this.currentPage = (currentPage<1)?1:currentPage;
		this.showCount = showCount;
		this.totalResult = totalResult;
		//������ҳ��
		if(totalResult==0){
			this.totalPage = 0;
		}else{
			this.totalPage = (totalResult%showCount==0)?(totalResult/showCount):(totalResult/showCount+1);
		}
		//�Ƿ�����һҳ
		this.hasPre = (currentPage==1)?false:true;
		//�Ƿ�����һҳ
		this.hasNext = (currentPage==totalPage)?false:true;
		//��һҳ
		if(hasPre){
			this.prePage = (currentPage-1);
		}
		//��һҳ
		if(hasNext){
			this.nextPage = currentPage+1;
		}
	}

	public int getTotalPage() {
		if(totalResult%showCount==0)
			totalPage = totalResult/showCount;
		else
			totalPage = totalResult/showCount+1;
		return totalPage;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}



	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		if(currentPage>getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if(totalResult>0){
			sb.append("	<ul>\n");
			if(currentPage==1){
				sb.append("	<li><a>��<font color=red>"+totalResult+"</font>��</a></li>\n");
				sb.append("	<li><input type=\"number\" value=\"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"ҳ��\"/></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">��ת</a></li>\n");
				sb.append("	<li><a>��ҳ</a></li>\n");
				sb.append("	<li><a>��ҳ</a></li>\n");
			}else{
				sb.append("	<li><a>��<font color=red>"+totalResult+"</font>��</a></li>\n");
				sb.append("	<li><input type=\"number\" value=\"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"ҳ��\"/></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">��ת</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\">��ҳ</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage-1)+")\">��ҳ</a></li>\n");
			}
			int showTag = 5;//��ҳ��ǩ��ʾ����
			int startTag = 1;
			if(currentPage>showTag){
				startTag = currentPage-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(currentPage==i)
					sb.append("<li><a><font color='#808080'>"+i+"</font></a></li>\n");
				else
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
			}
			if(currentPage==totalPage){
				sb.append("	<li><a>��ҳ</a></li>\n");
				sb.append("	<li><a>βҳ</a></li>\n");
			}else{
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage+1)+")\">��ҳ</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+totalPage+")\">βҳ</a></li>\n");
			}
			sb.append("	<li><a>��"+currentPage+"ҳ</a></li>\n");
			sb.append("	<li><a>��"+totalPage+"ҳ</a></li>\n");


			sb.append("	<li><select title='��ʾ����' style=\"width:55px;float:left;\" onchange=\"changeCount(this.value)\">\n");
			sb.append("	<option value='"+showCount+"'>"+showCount+"</option>\n");
			sb.append("	<option value='10'>10</option>\n");
			sb.append("	<option value='20'>20</option>\n");
			sb.append("	<option value='30'>30</option>\n");
			sb.append("	<option value='40'>40</option>\n");
			sb.append("	<option value='50'>50</option>\n");
			sb.append("	<option value='60'>60</option>\n");
			sb.append("	<option value='70'>70</option>\n");
			sb.append("	<option value='80'>80</option>\n");
			sb.append("	<option value='90'>90</option>\n");
			sb.append("	<option value='99'>99</option>\n");
			sb.append("	</select>\n");
			sb.append("	</li>\n");



			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");

			//��ҳ����
			sb.append("function nextPage(page){");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");

			//����ÿҳ��ʾ����
			sb.append("function changeCount(value){");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"1&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"1&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");

			//��ת����
			sb.append("function toTZ(){");
			sb.append("var toPaggeVlue = document.getElementById(\"toGoPage\").value;");
			sb.append("if(toPaggeVlue == ''){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("if(isNaN(Number(toPaggeVlue))){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("nextPage(toPaggeVlue);");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		/*pageStr = sb.toString();*/
		return pageStr==null?sb.toString():"";
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {

		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

//	public PageData getPd() {
//		return pd;
//	}
//
//	public void setPd(PageData pd) {
//		this.pd = pd;
//	}

	public List<String> getChildOrganIds() {
		return childOrganIds;
	}

	public void setChildOrganIds(List<String> childOrganIds) {
		this.childOrganIds = childOrganIds;
	}

	public List<String> getCompanyStates() {
		return companyStates;
	}

	public void setCompanyStates(List<String> companyStates) {
		this.companyStates = companyStates;
	}

	public List<String> getCompanyTypes() {
		return companyTypes;
	}

	public void setCompanyTypes(List<String> companyTypes) {
		this.companyTypes = companyTypes;
	}
	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}
