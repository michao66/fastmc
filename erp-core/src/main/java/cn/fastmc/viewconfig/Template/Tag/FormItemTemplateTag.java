package cn.fastmc.viewconfig.Template.Tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.viewconfig.components.Fieldset;
import cn.fastmc.viewconfig.components.Form;
import cn.fastmc.viewconfig.components.FormItem;
import cn.fastmc.viewconfig.components.FormRow;
import cn.fastmc.viewconfig.components.ViewBase;

public class FormItemTemplateTag extends TemplateBodyTagSupport {

	private static final Log LOG = LogFactory.getLog(FormItemTemplateTag.class);
	private List<FormItem> hiddenFormItem = new ArrayList<FormItem>();
	private Form form =  null;
	public FormTemplateTag getParentTag(){
		return ((FormTemplateTag)this.getParent());
	}

	@Override
	public int doEndTag() throws JspException {
		form = getParentTag().getForm();
		JspWriter writer = pageContext.getOut();

		List<List<FormItem>> group = form.getGroup();
		for (List<FormItem> row : group) {
			printClumn(writer, row);
		}

		for (FormItem item : hiddenFormItem) {
			item.end(pageContext);
		}
		hiddenFormItem.clear();
		return EVAL_PAGE;
	}
	
	private void printClumn(JspWriter writer, List<FormItem> items) {
		try {
			if (items.size() == 1 &&  items.get(0) instanceof Fieldset) {
				((Fieldset)items.get(0)).end(pageContext);
			} else {
				FormRow row = new FormRow();
				row.start(pageContext);
				for (FormItem item : items) {
					if (item.isUpdateHidde() && getParentTag().isUpdateState()) {
						continue;
					}
					
					if (item.isHidden()) {
						hiddenFormItem.add(item);
						continue;
					}
					item.end(pageContext);
				}
				row.end(pageContext);
			}

		} catch (Exception ex) {
			LOG.error(ex);
		}
	}
	
	

	public int doStartTag() throws JspException {
		
			
		return SKIP_BODY;
	}
	@Override
	public ViewBase getView() {
		// TODO Auto-generated method stub
		return null;
	}
}
