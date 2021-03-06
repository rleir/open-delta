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

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.org.ala.delta.DeltaContext;
import au.org.ala.delta.directives.args.DirectiveArgType;
import au.org.ala.delta.directives.args.DirectiveArguments;

public abstract class AbstractTextDirective extends AbstractDirective<DeltaContext> {

	private static Pattern VAR_PATTERN = Pattern.compile("[#]([A-Z]+)");
	
	protected String _args;
	
	protected AbstractTextDirective(String... controlWords) {
		super(controlWords);
	}

	@Override
	public DirectiveArguments getDirectiveArgs() {
		DirectiveArguments args = new DirectiveArguments();
		args.addTextArgument(_args);
		return args;
	}

	@Override
	public int getArgType() {
		return DirectiveArgType.DIRARG_TEXT;
	}
	
	
	@Override
	public void parse(DeltaContext context, String data) throws ParseException {
		_args = data;
		
	}

	protected String replaceVariables(DeltaContext context, String str) {
		String result = str;
		Matcher m = VAR_PATTERN.matcher(str);	
		while (m.find()) {
			String varname = m.group(1);
			String value = context.getVariable(varname, "#" + varname).toString();
			result = result.replaceAll("[#]" + varname, value);			
		}
		return result;		
	}
}
