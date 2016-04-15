package com.nearerToThee.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A complete Java program that demonstrates how to
 * extract a tag from a line of HTML using the Pattern
 * and Matcher classes.
 */
public class PatternMatcherGroupHtml {

    public String getStringBetweenTags(String stringToSearch, String pattern)
    {
        // the pattern we want to search for
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(stringToSearch);

        // if we find a match, get the group
        String codeGroup = null;
        if (m.find())
        {
            // get the matching group
            codeGroup = m.group(1);
        }
        return codeGroup;
    }
}
