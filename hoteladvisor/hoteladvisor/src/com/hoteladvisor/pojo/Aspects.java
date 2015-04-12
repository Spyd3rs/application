package com.hoteladvisor.pojo;

import java.util.Collection;
import java.util.Set;

public class Aspects {

	Set<AspectWithPolarity> aspects;

	public Aspects(Set<AspectWithPolarity> aspects) {
		this.aspects = aspects;
	}

	public Collection<AspectWithPolarity> getAspects() {
		return aspects;
	}

	public void setAspects(Set<AspectWithPolarity> aspects) {
		this.aspects = aspects;
	}

}
