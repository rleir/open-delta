/*******************************************************************************
 * Copyright (C) 2011 Atlas of Living Australia
 * All Rights Reserved.
 * 
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 ******************************************************************************/
package au.org.ala.delta.directives;

import au.org.ala.delta.DeltaContext;
import au.org.ala.delta.model.TypeSettingMark;
import au.org.ala.delta.model.TypeSettingMark.MarkPosition;

/**
 * Processes the TYPESETTING MARKS directive.
 */
public class TypeSettingMarks extends AbstractFormattingDirective {

	public TypeSettingMarks() {
		super("typesetting", "marks");
	}
	
	@Override
	public void processMark(DeltaContext context, TypeSettingMark mark) {
		
		String text = mark.getMarkText();
		String replacement = " ";
		if (!mark.getAllowLineBreaks()) {
			replacement = "";
		}
		text = text.replaceAll("[\\r\\n]+", replacement);
		TypeSettingMark newMark = new TypeSettingMark(mark.getId(), text, mark.getAllowLineBreaks());
		context.addTypeSettingMark(newMark);
		if (mark.getId() == MarkPosition.START_OF_FILE.getId()) {
			context.getOutputFileSelector().setPrintFileHeader(text);
		}
		else if (mark.getId() == MarkPosition.END_OF_FILE.getId()) {
			context.getOutputFileSelector().setPrintFileFooter(text);
		}
	}
	
	@Override
	public boolean canSpecifyTextDelimiter() {		
		return true;
	}
	
	@Override
	public int getOrder() {
		return 4;
	}

}
