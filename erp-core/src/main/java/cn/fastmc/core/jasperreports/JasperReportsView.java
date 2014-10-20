package cn.fastmc.core.jasperreports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.core.utils.RequestUtils;


/**
 * JasperReport 输出控制
 * 用于JasperReport 输出html fdf word各种格式视图
 * expType 输出视图类型  pdf,text,html,excel,rtf,xml
 * jasperFile 报表文件名
 * subreport 子报表文件名
 * @author michao
 *
 */
public class JasperReportsView extends HttpServlet {

	private ServletConfig config;
    private static String REPORT_FILE_DIR = "newreportfile";
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,response);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.config = config;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	
		Map<String,String> parameters = RequestUtils.getQueryParams(request);
		String exp = request.getParameter("expType");
		String beanType = request.getParameter("beanType");
		String jasperFile = request.getParameter("jasperFile");
		String subfile = request.getParameter("subreport");
		if("applets".equals(exp)&&parameters.get("airName")!=null && StringUtils.isNotEmpty((String)parameters.get("airName"))){
			String m3 = new String(((String)parameters.get("airName")).getBytes("iso-8859-1"),"UTF-8");
			parameters.put("airName",m3);
		}
		ServletContext servletContext = request.getSession().getServletContext();
		if(null != parameters.get("imagePath")){
			parameters.put("imagePath", servletContext.getRealPath((String)parameters.get("imagePath")));
		}
		byte[] output = null;
		JasperPrint jasperPrint = null;
		String readjasperFile = servletContext.getRealPath(REPORT_FILE_DIR+"\\"+jasperFile);
        if(StringUtils.isNotBlank(subfile)){
        	parameters.put("SUBREPORT_DIR", servletContext.getRealPath(subfile)+"\\");
        }else{
        	parameters.put("SUBREPORT_DIR", servletContext.getRealPath(REPORT_FILE_DIR)+"\\");
        }
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(readjasperFile);
			if (StringUtils.isEmpty(beanType)) {
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				String dataSource = this.config.getInitParameter("dataSource");
				DataSource reportSource = (DataSource) wc.getBean(dataSource);
				jasperPrint = fillReportForJdbc(parameters, reportSource,jasperReport);
			} else {
				
				 WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				 JRBeanCollectionDataProvide provide = (JRBeanCollectionDataProvide)wc.getBean(beanType);
				 JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(provide.getListBean(parameters));
				 jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,jrbean);

			}
			JRExporter exporter = null;

			if ("pdf".equals(exp)) {
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + getFileName() + ".pdf");
				exporter = new JRPdfExporter();
			} else if ("text".equals(exp)) {
				response.setContentType("text/plain");
				exporter = new JRCsvExporter();
			} else if ("html".equals(exp)) {
				response.setContentType("text/html;charset=UTF-8");// ;charset=gb2312
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				 String pageStr = request.getParameter("page");
				 int pageIndex = 0;
				 int lastPageIndex = 0;
				 if (jasperPrint.getPages() != null){
						lastPageIndex = jasperPrint.getPages().size() - 1;
				 }

				 pageIndex = NumberUtils.toInt(pageStr,0);
				 pageIndex = pageIndex-1 ;
				 if (pageIndex < 0){
						pageIndex = 0;
				 }

				 if (pageIndex > lastPageIndex){
						pageIndex = lastPageIndex;
				 }
			

				exporter = new JRHtmlExporter();
				StringBuffer sbuffer = new StringBuffer();
				sbuffer.append(htmlParam(request,pageIndex,lastPageIndex)) ;
				StringBuffer sbuffer2 = new StringBuffer();
				sbuffer2.append("</td></tr>");
				sbuffer2.append("</table>");
				sbuffer2.append("<script language=\"javascript\">");
				//设置分页显示值
				sbuffer2.append("document.getElementById( \"t_page_span\" ).innerHTML=report1_getTotalPage();");
				sbuffer2.append("document.getElementById( \"c_page_span\" ).innerHTML=report1_getCurrPage();");

				sbuffer2.append("</script>");
				sbuffer2.append("</body>");
			
				
			    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/report/ImageServlet?image="); 

			    exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX, pageIndex);
			    exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,servletContext.getRealPath("/reports")+"\\");
			    
			    exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			    exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "pt");//
			    
				exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, sbuffer.toString());
				exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, sbuffer2.toString());
				
			    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		
				output = String.valueOf(sbuffer).getBytes();  

			} else if ("excel".equals(exp)) {

				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + getFileName() + ".xls");
				exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);

			} else if ("xml".equals(exp)) {
				response.setContentType("text/xml");
				exporter = new JRXmlExporter();
			} else if ("rtf".equals(exp)) {
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + getFileName() + ".rtf");
				exporter = new JRRtfExporter();
           }
		if (null!=exporter)
			
				output = exportReportToBytes(jasperPrint, exporter);
		
			
		} catch (Exception e) {
			String message = "Error producing " + exp + " report for uri "+ readjasperFile;

			throw new ServletException(message, e);
		}
		if (output != null && output.length > 0) {
			response.setContentLength(output.length);
			writeReport(response, output);
		}
		if ("applets".equals(exp)) {
			response.setContentType("application/octet-stream");
			ServletOutputStream ouputStream = response.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
			oos.writeObject(jasperPrint);
			oos.flush();
			oos.close();

			ouputStream.flush();
			ouputStream.close();

		}

	}
	
	private String getFileName(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		String strDate = formatter.format( new Date());
		return strDate;
	}

	private JasperPrint fillReportForJdbc(Map parameters,
			DataSource reportSource, JasperReport jasperReport)
			throws SQLException, JRException {
		JasperPrint jasperPrint;
		Connection conn = reportSource.getConnection();
		try{
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
		
		}finally{
			conn.close();
		}
		return jasperPrint;
	}
	
	
	private StringBuffer htmlParam(HttpServletRequest request,int pageIndex,int lastPageIndex ){
		Map orgParam = request.getParameterMap();
		StringBuffer sbuffer = new StringBuffer();
		String appmap = request.getContextPath();
		sbuffer.append("<html>");
		sbuffer.append("<script type=\"text/javascript\" src='"+ appmap + "/resource/js/deployJava.js'></script>");  
		sbuffer.append("<script type=\"text/javascript\">");
		sbuffer.append("var jre = deployJava.getJREs(); ");
		sbuffer.append("if (\"\" == jre || null == jre ){");
		sbuffer.append("alert(\"请安装jre\");"); 
		sbuffer.append("window.location = '"+appmap+"/installLatestJRE.html';");  
		sbuffer.append("} ");
		sbuffer.append("</script>");
		sbuffer.append("<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>");
			String printImage = "<img src='" + appmap + "/resource/images/print.gif' border=no >";
			String excelImage = "<img src='" + appmap + "/resource/images/excel.gif' border=no >";
			String pdfImage = "<img src='" + appmap + "/resource/images/pdf.gif' border=no >";
		    String wordImage = "<img src='" + appmap + "/resource/images/doc.gif' border=no >";
			String firstPageImage = "<img src='" + appmap + "/resource/images/firstpage.gif' border=no >";
			String lastPageImage = "<img src='" + appmap + "/resource/images/lastpage.gif' border=no >";
			String nextPageImage = "<img src='" + appmap + "/resource/images/nextpage.gif' border=no >";
			String prevPageImage = "<img src='" + appmap + "/resource/images/prevpage.gif' border=no >";
			String submitImage = "<img src='" + appmap + "/resource/images/savedata.gif' border=no >";
			
			sbuffer.append("<table id=titleTable width=100% cellspacing=0 cellpadding=0 border=0 ><tr>");
			sbuffer.append("<td height=\"22\" width=100% valign=\"middle\"  style=\"font-size:13px\" background=\"../images/ta51top.jpg\">");
			sbuffer.append("<table width=\"100%\">");
			sbuffer.append("<tr >");
			sbuffer.append("<td width=53% align=\"left\"  style=\"font-size:13px\" >&nbsp;&nbsp;&nbsp;&nbsp;</td>");
			sbuffer.append("<td width=\"47%\" align=\"center\" valign=\"middle\"   style=\"font-size:12px\">共<span id=\"t_page_span\"></span>页/第<span id=\"c_page_span\"></span>页&nbsp;&nbsp;");
			sbuffer.append("<a href=\"#\" onClick=\"report1_print();return false;\" title=\"打印\">"+printImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"report1_saveAsExcel();return false;\" title=\"保存为Excel\">"+excelImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"report1_saveAsPdf();return false;\" title=\"保存为PDF\">"+pdfImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"report1_saveAsWord();return false;\" title=\"保存为Word\">"+wordImage+"</a>");
					
			
					
		    sbuffer.append("<a href=\"#\" onClick=\"try{report1_toPage( 1 );}catch(e){}return false;\" title=\"首页\">"+firstPageImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"try{report1_toPage(report1_getCurrPage()-1);}catch(e){}return false;\" title=\"上一页\">"+prevPageImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"try{report1_toPage(report1_getCurrPage()+1);}catch(e){}return false;\" title=\"下一页\">"+nextPageImage+"</a>");
			sbuffer.append("<a href=\"#\" onClick=\"try{report1_toPage(report1_getTotalPage());}catch(e){}return false;\" title=\"尾页\">"+lastPageImage+"</a>");
			sbuffer.append(" </td>");
			sbuffer.append("</tr>");
			sbuffer.append(" </table>");
			sbuffer.append("</td>");
			sbuffer.append("</table>");

	
		
		
		
		String scheme = request.getScheme(); 
	    String serverName = request.getServerName(); 
	    int serverPort = request.getServerPort(); 
	    String contextPath = request.getContextPath();  
	    String url="paramUrl=";
		sbuffer.append("<form name=\"report1_turnPageForm\" method=post action=\""+scheme+"://"+serverName+":"+serverPort+contextPath+"/report/jasperReportsView\" style=\"display:none\">");
		Set keys = orgParam.keySet();  
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String strKey = (String) it.next();
			Object obj = orgParam.get(strKey);
			String value = null;
			if (obj instanceof Object[]) {
				value = (String)((Object[]) obj)[0];
			} else {
				value = (String) obj;
			}
			    if(!"page".equals(strKey)&&!"expType".equals(strKey)){
			    	 url = url+strKey+":"+value+";";
			    	 sbuffer.append("<input type=hidden name=\""+strKey+"\" value=\""+value+"\">");
			    }
		  }
		  sbuffer.append("<input type=hidden name=\"page\" value=\"0\">");
		  sbuffer.append("<input type=hidden name=\"expType\" value=\"html\"></form>");
		  sbuffer.append("<script language=javascript>");
		  sbuffer.append("function report1_toPage( pageNo ) {");
		  sbuffer.append("if( pageNo < 1 || pageNo > report1_getTotalPage() ) return;");
		  sbuffer.append("document.report1_turnPageForm.expType.value = 'html';");
	      sbuffer.append("document.report1_turnPageForm.page.value = pageNo;");
		  sbuffer.append("document.report1_turnPageForm.submit();");
		  sbuffer.append("}");
		  
		  sbuffer.append("function report1_saveAsExcel() {");
		  sbuffer.append("document.report1_turnPageForm.expType.value = 'excel';");
		  sbuffer.append("document.report1_turnPageForm.submit();");
		  sbuffer.append("}");
		  
		  sbuffer.append("function report1_saveAsPdf() {");
		  sbuffer.append("document.report1_turnPageForm.expType.value = 'pdf';");
		  sbuffer.append("document.report1_turnPageForm.submit();");
		  sbuffer.append("}");
		  
		  sbuffer.append("function report1_saveAsWord() {");
		  sbuffer.append("document.report1_turnPageForm.expType.value = 'rtf';");
		  sbuffer.append("document.report1_turnPageForm.submit();");
		  sbuffer.append("}");
		  //
		  sbuffer.append("function report1_print() {");
		  sbuffer.append("document.report1_printIFrame.location = \""+scheme+"://"+serverName+":"+serverPort+contextPath+"/appletView.jsp?"+url+"\"");
		  sbuffer.append("}");
		  
		  sbuffer.append("function report1_getCurrPage() {");
		  sbuffer.append("return "+(pageIndex+1)+";");
		  sbuffer.append("}");
		  sbuffer.append("function report1_getTotalPage() {");
		  sbuffer.append("return "+(lastPageIndex+1)+";");
		  sbuffer.append("}");
		
		  sbuffer.append("function open_window(url, width, height){");
		  sbuffer.append("windowFeatures = \"width=\" + width + \",height=\" + height + \",resizable=1,scrollbars=1\";");
		  sbuffer.append("windowName = \"new_window\"+Math.round(Math.random()*10000);");
		  sbuffer.append("win = window.open(url, windowName, windowFeatures);");
		  sbuffer.append("if (window.focus) {");
		  sbuffer.append("win.focus();");
		  sbuffer.append("}");
		  sbuffer.append("return win;");
		  sbuffer.append("}");

		  sbuffer.append("</script>");
		  sbuffer.append("<iframe name=\"report1_printIFrame\" id=\"report1_printIFrame\" src=\"#\" style=\"position:absolute;left:-100px;top:-100px\" width=50 height=50></iframe>");
		  sbuffer.append("<table align=center>");
	      sbuffer.append("<tr><td>");
		  return sbuffer;

	}

	
	private byte[] exportReportToBytes(JasperPrint jasperPrint,
			JRExporter exporter) throws JRException {
		byte[] output;
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		output = baos.toByteArray();
		return output;
	}

	private void writeReport(HttpServletResponse response, byte[] output)
			throws ServletException {
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(output);
			outputStream.flush();
		} catch (IOException e) {
			// LOG.error("Error writing report output", e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				// /LOG.error("Error closing report output stream", e);
				throw new ServletException(e.getMessage(), e);
			}
		}
	}
}