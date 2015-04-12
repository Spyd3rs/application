package com.hoteladvisor.pojo;

public class AspectWithPolarity {

	String aspect;
	Integer polarity;

	public String getAspect() {
		return aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}

	public Integer getPolarity() {
		return polarity;
	}

	public void setPolarity(Integer polarity) {
		this.polarity = polarity;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof AspectWithPolarity) {

			return this.aspect.equals(((AspectWithPolarity) obj).getAspect());

		}

		return false;
	}

	@Override
	public int hashCode() {

		return aspect.hashCode();

	}
}
