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
package au.org.ala.delta.confor;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import au.org.ala.delta.intkey.model.IntkeyDataset;
import au.org.ala.delta.intkey.model.IntkeyDatasetFileReader;
import au.org.ala.delta.model.Attribute;
import au.org.ala.delta.model.Character;
import au.org.ala.delta.model.CharacterType;
import au.org.ala.delta.model.IntegerAttribute;
import au.org.ala.delta.model.Item;
import au.org.ala.delta.model.MultiStateAttribute;
import au.org.ala.delta.model.MultiStateCharacter;
import au.org.ala.delta.model.NumericCharacter;
import au.org.ala.delta.model.RealAttribute;
import au.org.ala.delta.model.RealCharacter;
import au.org.ala.delta.model.TextAttribute;

/**
 * Tests the CONFOR toint process.
 */
public class ToIntTest extends TestCase {
	
	@Test
	public void testSampleToInt() throws Exception {
		
		File tointDirectory = urlToFile("/dataset/");
		File dest = new File(System.getProperty("java.io.tmpdir"));
		FileUtils.copyDirectory(tointDirectory, dest);
		
		String tointFilePath = FilenameUtils.concat(dest.getAbsolutePath(), "sample/toint");
		
		CONFOR.main(new String[]{tointFilePath});
		
		File ichars = new File(FilenameUtils.concat(dest.getAbsolutePath(), "sample/ichars"));
		File iitems = new File(FilenameUtils.concat(dest.getAbsolutePath(), "sample/iitems"));
		
		IntkeyDataset dataSet = IntkeyDatasetFileReader.readDataSet(ichars, iitems);
		
		
		File expectedIChars = new File(FilenameUtils.concat(dest.getAbsolutePath(), "sample/expected_results/ichars"));
		File expectedIItems = new File(FilenameUtils.concat(dest.getAbsolutePath(), "sample/expected_results/iitems"));
		
		IntkeyDataset expectedDataSet = IntkeyDatasetFileReader.readDataSet(expectedIChars, expectedIItems);
		
		compare(dataSet, expectedDataSet);
		
	}
	
	@Test
	public void zztestPoneriniToInt() throws Exception {
		File tointDirectory = urlToFile("/dataset/");
		File dest = new File(System.getProperty("java.io.tmpdir"));
		FileUtils.copyDirectory(tointDirectory, dest);
		
		String tointFilePath = FilenameUtils.concat(dest.getAbsolutePath(), "ponerini/toint");
		
		CONFOR.main(new String[]{tointFilePath});
		
		File ichars = new File(FilenameUtils.concat(dest.getAbsolutePath(), "ponerini/ichars"));
		File iitems = new File(FilenameUtils.concat(dest.getAbsolutePath(), "ponerini/iitems"));
		
		IntkeyDataset dataSet = IntkeyDatasetFileReader.readDataSet(ichars, iitems);
		
		
		File expectedIChars = new File(FilenameUtils.concat(dest.getAbsolutePath(), "ponerini/expected_results/ichars"));
		File expectedIItems = new File(FilenameUtils.concat(dest.getAbsolutePath(), "ponerini/expected_results/iitems"));
		
		IntkeyDataset expectedDataSet = IntkeyDatasetFileReader.readDataSet(expectedIChars, expectedIItems);
		
		compare(dataSet, expectedDataSet);
	}
	

	protected void compare(IntkeyDataset dataSet, IntkeyDataset expectedDataSet) {
		/*BinaryKeyFile file = new BinaryKeyFile(FilenameUtils.concat(dest.getAbsolutePath(), "sample/ichars"), BinFileMode.FM_READONLY);
		BinaryKeyFile efile = new BinaryKeyFile(FilenameUtils.concat(dest.getAbsolutePath(), "sample/expected_results/ichars"), BinFileMode.FM_READONLY);
		
		List<Integer> header = file.readIntegerList(1, 128);
		List<Integer> eheader = efile.readIntegerList(1, 128);
		//assertEquals(eheader, header);
		
		BinaryKeyFile filec = new BinaryKeyFile(FilenameUtils.concat(dest.getAbsolutePath(), "sample/iitems"), BinFileMode.FM_READONLY);
		BinaryKeyFile efilec = new BinaryKeyFile(FilenameUtils.concat(dest.getAbsolutePath(), "sample/expected_results/iitems"), BinFileMode.FM_READONLY);
		
		List<Integer> headerc = filec.readIntegerList(1, 32);
		headerc.addAll(filec.readIntegerList(5704, 32));
		List<Integer> eheaderc = efilec.readIntegerList(1, 64);
		//assertEquals(eheaderc, headerc);
		*/
		
		assertEquals(expectedDataSet.getNumberOfCharacters(), dataSet.getNumberOfCharacters());
		assertEquals(expectedDataSet.getNumberOfTaxa(), dataSet.getNumberOfTaxa());
	
		assertEquals(expectedDataSet.getCharacterKeywordImages(), dataSet.getCharacterKeywordImages());
		assertEquals(expectedDataSet.getHeading(), dataSet.getHeading());
		// These seem to be unused now.
		//assertEquals(expectedDataSet.getHelpCharNotesFormattingInfo(), dataSet.getHelpCharNotesFormattingInfo());
		//assertEquals(expectedDataSet.getMainCharNotesFormattingInfo(), dataSet.getMainCharNotesFormattingInfo());
		assertEquals(expectedDataSet.getOrWord(), dataSet.getOrWord());
		assertEquals(expectedDataSet.getSubHeading(), dataSet.getSubHeading());
		assertEquals(expectedDataSet.getValidationString(), dataSet.getValidationString());
		assertEquals(expectedDataSet.getOverlayFonts(), dataSet.getOverlayFonts());
		assertEquals(expectedDataSet.getStartupImages(), dataSet.getStartupImages());
		assertEquals(expectedDataSet.getSynonymyCharacters(), dataSet.getSynonymyCharacters());
		assertEquals(expectedDataSet.getTaxonKeywordImages(), dataSet.getTaxonKeywordImages());
		
		Map<Item, List<TextAttribute>> expectedSynonomy = expectedDataSet.getSynonymyAttributesForTaxa();
		Map<Item, List<TextAttribute>> actualSynonomy = dataSet.getSynonymyAttributesForTaxa();
		
		for (Item item : expectedSynonomy.keySet()) {
			List<TextAttribute> expectedAttrs = expectedSynonomy.get(item);
			List<TextAttribute> actualAttrs = actualSynonomy.get(item);
			
			assertEquals(expectedAttrs.size(), actualAttrs.size());
			for (int i=0; i<expectedAttrs.size(); i++) {
				TextAttribute expectedAttr = expectedAttrs.get(i);
				TextAttribute actualAttr = actualAttrs.get(i);
				
				assertEquals(expectedAttr.getValueAsString(), actualAttr.getValueAsString());
			}
		}
		
		for (int i=1; i<=expectedDataSet.getNumberOfTaxa(); i++) {
			Item item = dataSet.getItem(i);
			Item expectedItem = expectedDataSet.getItem(i);
			
			assertEquals(expectedItem.getDescription(), item.getDescription());
			assertEquals(expectedItem.getImages(), item.getImages());
			assertEquals(expectedItem.getLinkFiles(), item.getLinkFiles());
			
			for (int j=1; j<=expectedDataSet.getNumberOfCharacters(); j++) {
				Character character = dataSet.getCharacter(j);
				Character expectedCharacter = expectedDataSet.getCharacter(j);
				
				assertEquals("Character: "+j,expectedCharacter.getCharacterType(), character.getCharacterType());
				if (expectedCharacter.getCharacterType().isMultistate()) {
					MultiStateCharacter multiStateChar = (MultiStateCharacter)character;
					MultiStateCharacter expectedMultiStateChar = (MultiStateCharacter)expectedCharacter;
					
					assertEquals(expectedMultiStateChar.getNumberOfStates(), multiStateChar.getNumberOfStates());
					for (int k=1; k<=expectedMultiStateChar.getNumberOfStates(); k++) {
						assertEquals("Character: "+j,expectedMultiStateChar.getState(k), multiStateChar.getState(k));
					}
				}
				else if (expectedCharacter.getCharacterType().isNumeric()) {
					
					NumericCharacter<?> numericChar = (NumericCharacter<?>)character;
					NumericCharacter<?> expectedNumericChar = (NumericCharacter<?>)expectedCharacter;
					
					assertEquals(expectedNumericChar.getUnits(), numericChar.getUnits());
					
					if (expectedCharacter.getCharacterType() == CharacterType.RealNumeric) {
						RealCharacter realChar = (RealCharacter)character;
						RealCharacter expectedRealChar = (RealCharacter)expectedCharacter;
						
						assertEquals("Character: "+j,expectedRealChar.getKeyStateBoundaries(), realChar.getKeyStateBoundaries());
					}
				}
				
				assertEquals(expectedCharacter.getDescription(), character.getDescription());
				assertEquals(expectedCharacter.getContainsSynonmyInformation(), character.getContainsSynonmyInformation());
				assertEquals(expectedCharacter.getImages(), character.getImages());
				assertEquals(expectedCharacter.getItemSubheading(), character.getItemSubheading());
				assertEquals(expectedCharacter.getNewParagraph(), character.getNewParagraph());
				assertEquals(expectedCharacter.getNonAutoCc(), character.getNonAutoCc());
				assertEquals(expectedCharacter.getNotes(), character.getNotes());
				assertEquals(expectedCharacter.getOmitOr(), character.getOmitOr());
				assertEquals(expectedCharacter.getOmitPeriod(), character.getOmitPeriod());
				assertEquals(expectedCharacter.getReliability(), character.getReliability(), 0.016f);
				assertEquals(expectedCharacter.getUseCc(), character.getUseCc());
				assertEquals(expectedCharacter.getControllingCharacters(), character.getControllingCharacters());
				assertEquals(expectedCharacter.getDependentCharacters(), character.getDependentCharacters());
				
				
				Attribute expectedAttribute = expectedDataSet.getAttribute(i, j);
				Attribute attr = dataSet.getAttribute(i, j);
				
				if (expectedAttribute.isInapplicable() != attr.isInapplicable()) {
					System.out.println("attribute: "+i+","+j+" inapplicable wrong");
				}
				assertEquals(expectedAttribute.isInapplicable(), attr.isInapplicable());
				assertEquals(expectedAttribute.getValueAsString(), attr.getValueAsString());
				if (expectedAttribute instanceof MultiStateAttribute) {
					assertEquals("attribute: "+i+","+j, ((MultiStateAttribute)expectedAttribute).getPresentStates(),
							((MultiStateAttribute)attr).getPresentStates());
				}
				else if (expectedAttribute instanceof RealAttribute) {
					assertEquals("attribute: "+i+","+j, ((RealAttribute)expectedAttribute).getPresentRange(),
							((RealAttribute)attr).getPresentRange());
				}
				else if (expectedAttribute instanceof IntegerAttribute) {
					assertEquals("attribute: "+i+","+j, ((IntegerAttribute)expectedAttribute).getPresentValues(),
							((IntegerAttribute)attr).getPresentValues());
				}
			}
			
		}
	}
	
	private File urlToFile(String urlString) throws Exception {
		URL url = ToIntTest.class.getResource(urlString);
		File file = new File(url.toURI());
		return file;
	}
}
