package cn.fastmc.viewconfig.Template;

import java.io.IOException;
import java.io.Writer;

import cn.fastmc.core.ServiceException;
import cn.fastmc.core.utils.FreeMarkerUtils;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
public class FreemarkerTemplateEngine {
    
	public static void renderTemplate(TemplateRenderingContext templateContext,String templateNamep) throws ServiceException {
		try {
			Configuration config = FreeMarkerUtils.getFreeMarkerConfiguration();
			Template template = config.getTemplate(templateNamep);
			SimpleHash model =   FreeMarkerUtils.buildTemplateModel(templateContext.getServletContext(), templateContext.getRequest(), templateContext.getResponse());
			model.put("tag",templateContext.getTag());
			Writer writer = templateContext.getWriter();
			final Writer wrapped = writer;
			writer = new Writer() {
				public void write(char cbuf[], int off, int len)
						throws IOException {
					wrapped.write(cbuf, off, len);
				}

				public void flush() throws IOException {

				}

				public void close() throws IOException {
					wrapped.close();
				}
			};

			template.process(model, writer);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}

	}

}
