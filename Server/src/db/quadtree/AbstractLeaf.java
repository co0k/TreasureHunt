package org.apptirol.innsbruckmastermeter.model.quadTree;

/* *********************************************************************** *
 * AbstractLeaf.java                                                       *
 * *********************************************************************** *
 * date created    : August, 2012                                          *
 * email           : info@kirstywilliams.co.uk                             *
 * author          : Kirsty Williams                                       *
 * version         : 1.0                                                   *
 * *********************************************************************** */

public class AbstractLeaf<T> {
	public QuadLeaf<T> value;

	public AbstractLeaf(QuadLeaf<T> value) {
		this.value = value;
	}
}