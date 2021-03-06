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
package au.org.ala.delta.model.attribute;

import junit.framework.TestCase;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Tests the DecimalPlacesAttributeChunkFormatter class.
 */
public class DecimalPlacesAttributeChunkFormatterTest extends TestCase {

    @Test
    public void testFormatNumber() {

        String[] input = new String[] {"0.000001", "10.123", "10", "123456.00", "1234.5", "1234.55", "4444.4444", "1.00", "12345.0"};

        int decimalPlaces = 2;
        String[] expected = new String[] {"0.00", "10.12", "10.00", "123456.00", "1234.50", "1234.55", "4444.44", "1.00", "12345.00"};
        DecimalPlacesAttributeChunkFormatter formatter = new DecimalPlacesAttributeChunkFormatter(false, "-", decimalPlaces);
        for (int i=0; i<input.length; i++) {
            String result = formatter.formatNumber(new BigDecimal(input[i]));
            assertEquals(input[i], expected[i], result);
        }
    }


}
