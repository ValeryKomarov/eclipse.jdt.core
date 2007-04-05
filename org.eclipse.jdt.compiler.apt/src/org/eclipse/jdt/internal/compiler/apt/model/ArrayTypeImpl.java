/*******************************************************************************
 * Copyright (c) 2007 BEA Systems, Inc. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    wharley@bea.com - initial API and implementation
 *    
 *******************************************************************************/

package org.eclipse.jdt.internal.compiler.apt.model;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;

/**
 * Implementation of ArrayType, which represents an array of some type.
 */
public class ArrayTypeImpl extends TypeMirrorImpl implements ArrayType {

	ArrayTypeImpl(ArrayBinding binding) {
		super(binding);
	}

	/* (non-Javadoc)
	 * @see javax.lang.model.type.ArrayType#getComponentType()
	 */
	@Override
	public TypeMirror getComponentType() {
		return Factory.newTypeMirror(((ArrayBinding)_binding).elementsType());
	}

	/* (non-Javadoc)
	 * @see javax.lang.model.type.TypeMirror#accept(javax.lang.model.type.TypeVisitor, java.lang.Object)
	 */
	@Override
	public <R, P> R accept(TypeVisitor<R, P> v, P p) {
		return v.visitArray(this, p);
	}

}
