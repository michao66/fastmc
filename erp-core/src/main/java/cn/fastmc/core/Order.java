package cn.fastmc.core;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.domain.Sort.Direction;

public class Order implements Serializable {
	public enum Direction {
		asc, desc;
		public static Direction fromString(String value) {
			return valueOf(value.toLowerCase());
		}
	}

	private static final Order.Direction DEFAULT_DIRECTION = Order.Direction.desc;
	private String property;
	private Order.Direction direction = DEFAULT_DIRECTION;

	public Order() {
	}

	public Order(String property, Order.Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	public static Order asc(String property) {
		return new Order(property, Order.Direction.asc);
	}

	public static Order desc(String property) {
		return new Order(property, Order.Direction.desc);
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Order.Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Order.Direction direction) {
		this.direction = direction;
	}
	
	public boolean isAscending() {
		return this.direction.equals(Direction.asc);
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		Order order = (Order) obj;
		return new EqualsBuilder()
				.append(getProperty(), order.getProperty())
				.append(getDirection(), order.getDirection()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getProperty())
				.append(getDirection()).toHashCode();
	}
}
