package cn.fastmc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class pager<T> implements Serializable
{
  private static final long serialVersionUID = -2053800594583879853L;
  private final List<T> content = new ArrayList<T>();
  private final long total;
  private final Pageable pageable;

  public pager()
  {
    this.total = 0L;
    this.pageable = new Pageable();
  }

  public pager(List<T> content, long total, Pageable pageable)
  {
    this.content.addAll(content);
    this.total = total;
    this.pageable = pageable;
  }
  
  public pager(List<T> content) {
	 this(content, (null == content) ? 0 : content.size(), null);
  }

  public int getPageNumber() {
		return this.pageable.getPageNumber();
	}

	public int getPageSize() {
		return this.pageable.getPageSize();
	}

  public Map<String,String> getSearchParam()
  {
    return this.pageable.getSearchParam();
  }

  public String getOrderProperty()
  {
    return this.pageable.getOrderProperty();
  }

  public Order.Direction getOrderDirection()
  {
    return this.pageable.getOrderDirection();
  }

  public int getTotalPages(){
    return (int)Math.ceil((double)getTotal() / getPageSize());
  }

  public List<T> getContent(){
    return this.content;
  }

  public long getTotal(){
    return this.total;
  }

  public Pageable getPageable(){
    return this.pageable;
  }

}
