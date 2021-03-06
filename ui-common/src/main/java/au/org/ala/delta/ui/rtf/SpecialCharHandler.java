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
package au.org.ala.delta.ui.rtf;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import au.org.ala.delta.util.Pair;

public class SpecialCharHandler {
	
	private int _keyCode;
	private int _modifiers;
	
	private SpecialCharacterMode _mode;
	private Map<Character, Character> _charMap = new HashMap<Character, Character>();
	private boolean _hasBeenProcessed;
	private char _defaultChar; // the char to emit if space is the follow up key...
	
	protected static Pair<Character, Character> pair(char keyIn, char keyOut) {
		return new Pair<Character, Character>(keyIn, keyOut);
	}
	
	protected static Pair<Character, Character> pair(char keyIn, int keyOut) {
		return new Pair<Character, Character>(keyIn, (char) keyOut);
	}
	
	public SpecialCharHandler(int keyCode, int modifiers, SpecialCharacterMode mode, int defaultChar, Pair<Character, Character> ... mappings) {
		_keyCode= keyCode;
		_modifiers = modifiers;
		_mode = mode;
		_defaultChar = (char) defaultChar;
		for (int i = 0; i < mappings.length; i++) {
			_charMap.put(mappings[i].getFirst(), mappings[i].getSecond());
		}
	}
	
	public boolean processFollowUpKey(KeyEvent e, RtfEditor editor) {
		char next = e.getKeyChar();
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// Cancel the special text mode!
			_hasBeenProcessed = true;
			return true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			editor.insertCharAtCaret(_defaultChar);
			_hasBeenProcessed = true;
			return true;			
		}
		
		if (_charMap.containsKey(next)) {
			editor.insertCharAtCaret(_charMap.get(next));
			_hasBeenProcessed = true;
			return true;
		}
		return false;
	}
	
	public int getKeyCode() {
		return _keyCode;
	}
	
	public int getModifiers() {
		return _modifiers;
	}
	
	public boolean hasBeenProcessed() {
		return _hasBeenProcessed;
	}
	
	public void setHasBeenProcessed(boolean hasBeenProcessed) {
		_hasBeenProcessed = hasBeenProcessed;
	}
	
	public SpecialCharacterMode getMode() {
		return _mode;
	}
	 
}
