/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.core.util;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.core.SourceRefElement;

/*
 * Search a given AST for the ASTNote corresponding to a Java element.
 * Optionally resolve its binding.
 */
public class DOMFinder extends ASTVisitor {
	
	public ASTNode foundNode = null;
	public IBinding foundBinding = null;
	
	private CompilationUnit ast;
	private SourceRefElement element;
	private boolean resolveBinding;
	private int rangeStart = -1, rangeLength = 0;
	
	public DOMFinder(CompilationUnit ast, SourceRefElement element, boolean resolveBinding) {
		this.ast = ast;
		this.element = element;
		this.resolveBinding = resolveBinding;
	}
	
	protected boolean found(ASTNode node) {
		if (node.getStartPosition() == rangeStart && node.getLength() == rangeLength) {
			this.foundNode = node;
			return true;
		}
		return false;
	}
	
	public ASTNode search() throws JavaModelException {
		ISourceRange range = null;
		if (this.element instanceof IField || (this.element instanceof IType && ((IType) this.element).isAnonymous()))
			range = ((IMember) this.element).getNameRange();
		else
			range = this.element.getSourceRange();
		this.rangeStart = range.getOffset();
		this.rangeLength = range.getLength();
		this.ast.accept(this);
		return this.foundNode;
	}
	
	public boolean visit(AnnotationTypeDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(AnonymousClassDeclaration node) {
		ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) node.getParent();
		if (found(classInstanceCreation.getType()) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(EnumConstantDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveVariable();
		return true;
	}
	
	public boolean visit(EnumDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(ImportDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(MethodDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(TypeDeclaration node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(TypeParameter node) {
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
	
	public boolean visit(VariableDeclarationFragment node) {						
		if (found(node) && this.resolveBinding)
			this.foundBinding = node.resolveBinding();
		return true;
	}
}
