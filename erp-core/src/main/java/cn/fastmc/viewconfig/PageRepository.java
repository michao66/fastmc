package cn.fastmc.viewconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.viewconfig.components.Page;

public class PageRepository implements InitializingBean, DisposableBean {

	private static final Log LOG = LogFactory.getLog(PageRepository.class);
	final Map<String, Page> Pages = new HashMap<String, Page>();
	
	@Autowired  
	private ServletContext servletContext;

	private Digester configDigester;
	private static final String TEMP_START="/WEB-INF/jsp/";
	@Override
	public void destroy() throws Exception {
		Pages.clear();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	protected void init() throws IOException, SAXException {
		try {
			LOG.info("----start load pagConfig");
			Digester digester = initConfigDigester();
			Set<String> subResources = servletContext.getResourcePaths("/WEB-INF/page");
			
            for(String path : subResources){
            	LOG.info("----start load "+path);
            	digester.push(this);
				digester.parse(servletContext.getResourceAsStream(path));
            }
		} catch (IOException ex) {
			LOG.error(ex);
		} catch (SAXException sa) {
			LOG.error(sa);
		}
	}

	protected void parse(String path) {
		try {
			String temppath = TEMP_START + path + "/" + path + "_page.xml";
			String filePath = servletContext.getRealPath(temppath);
			if (FunctionUtils.isFileExist(filePath)) {
				InputStream inputstream = servletContext.getResourceAsStream(temppath);
				configDigester.push(this);
				configDigester.parse(inputstream);
			}
		} catch (IOException ex) {
			LOG.error(ex);
		} catch (SAXException sa) {
			LOG.error(sa);
		}
	}
	public Digester initConfigDigester() {
		configDigester = new Digester();
		configDigester.setValidating(false);
		configDigester.setUseContextClassLoader(true);
		configDigester.addRuleSet(new ViewRuleSet());
		return configDigester;
	}
	
	public void addPage(Page page){
		Pages.put(page.getId(), page);
	}
	public boolean exists(String pageId,String formId){
		if(Pages.get(pageId)!=null){
			return Pages.get(pageId).getForm(formId)!=null;
		}else{
			return false;
		}
	}
	
	public Page findPage(String path){
		if(!Pages.containsKey(path)){
			parse(path);
		}
		Page page = Pages.get(path);
		if(null == page){
			LOG.error("no find page path:"+path);
		}
		return page;
	}
	

	


}
