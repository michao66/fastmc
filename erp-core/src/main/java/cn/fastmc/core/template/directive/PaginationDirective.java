package cn.fastmc.core.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.fastmc.core.template.BaseDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("paginationDirective")
public class PaginationDirective extends BaseDirective{
	 @SuppressWarnings({"rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
	  {
		    Integer pageNumber = (Integer)getParameter("pageNumber", Integer.class, params);
		    Integer totalPages = (Integer)getParameter("totalPages", Integer.class, params);
		    Integer segmentCount = (Integer)getParameter("segmentCount", Integer.class, params);
		    
		    if ((pageNumber == null) || (pageNumber.intValue() < 1))
		    	pageNumber = Integer.valueOf(1);
		    if ((totalPages == null) || (totalPages.intValue() < 1))
		    	totalPages = Integer.valueOf(1);
		    if ((segmentCount == null) || (segmentCount.intValue() < 1))
		    	segmentCount = Integer.valueOf(5);
		    boolean hasPrevious = pageNumber.intValue() > 1;
		    boolean hasNext = pageNumber.intValue() < totalPages.intValue();
		    boolean isFirst = pageNumber.intValue() == 1;
		    boolean isLast = pageNumber.equals(totalPages);
		    int previousPageNumber = pageNumber.intValue() - 1;
		    int nextPageNumber = pageNumber.intValue() + 1;
		    int firstPageNumber = 1;
		    int lastPageNumber = totalPages.intValue();
		    int n = pageNumber.intValue() - (int)Math.floor((totalPages.intValue() - 1) / 2.0D);
		    int i1 = pageNumber.intValue() + (int)Math.ceil((totalPages.intValue() - 1) / 2.0D);
		    if (n < 1)
		      n = 1;
		    if (i1 > totalPages.intValue())
		      i1 = totalPages.intValue();
		   
			ArrayList segment = new ArrayList();
		    for (int i2 = n; i2 <= i1; i2++)
		    	segment.add(Integer.valueOf(i2));
		    HashMap model = new HashMap();
		    model.put("pageNumber", pageNumber);
		    model.put("totalPages", totalPages);
		    model.put("segmentCount", segmentCount);
		    model.put("hasPrevious", Boolean.valueOf(hasPrevious));
		    model.put("hasNext", Boolean.valueOf(hasNext));
		    model.put("isFirst", Boolean.valueOf(isFirst));
		    model.put("isLast", Boolean.valueOf(isLast));
		    model.put("previousPageNumber", Integer.valueOf(previousPageNumber));
		    model.put("nextPageNumber", Integer.valueOf(nextPageNumber));
		    model.put("firstPageNumber", Integer.valueOf(firstPageNumber));
		    model.put("lastPageNumber", Integer.valueOf(lastPageNumber));
		    model.put("segment", segment);
		    setVariables(model, env, body);
		  }
	  
}
