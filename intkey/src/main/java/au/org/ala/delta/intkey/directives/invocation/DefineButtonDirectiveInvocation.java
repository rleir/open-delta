package au.org.ala.delta.intkey.directives.invocation;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import au.org.ala.delta.intkey.model.IntkeyContext;

public class DefineButtonDirectiveInvocation extends IntkeyDirectiveInvocation {

    private boolean _displayAdvancedOnly;
    private boolean _displayNormalOnly;
    private boolean _inactiveUnlessCharactersUsed;

    private String _imageFileName;
    private List<String> _directivesToRun;
    private String _shortHelp;
    private String _fullHelp;

    public DefineButtonDirectiveInvocation(boolean displayAdvancedOnly, boolean displayNormalOnly, boolean inactiveUnlessCharactersUsed, String imageFileName, List<String> directivesToRun,
            String shortHelp, String fullHelp) {
        _displayAdvancedOnly = displayAdvancedOnly;
        _displayNormalOnly = displayNormalOnly;
        _inactiveUnlessCharactersUsed = inactiveUnlessCharactersUsed;

        _imageFileName = imageFileName;
        _directivesToRun = directivesToRun;
        _shortHelp = shortHelp;
        _fullHelp = fullHelp;
    }

    @Override
    public boolean execute(IntkeyContext context) {
        context.getUI().addToolbarButton(_displayAdvancedOnly, _displayNormalOnly, _inactiveUnlessCharactersUsed, _imageFileName, _directivesToRun, _shortHelp, _fullHelp);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringRepresentationBuilder = new StringBuilder();
        stringRepresentationBuilder.append("DEFINE BUTTON ");

        if (_displayAdvancedOnly) {
            stringRepresentationBuilder.append("/A ");
        }

        if (_displayNormalOnly) {
            stringRepresentationBuilder.append("/N ");
        }

        if (_inactiveUnlessCharactersUsed) {
            stringRepresentationBuilder.append("/U ");
        }

        stringRepresentationBuilder.append(String.format("\"%s\" ", _imageFileName));
        stringRepresentationBuilder.append(String.format("\"%s\" ", StringUtils.join(_directivesToRun, ";")));
        stringRepresentationBuilder.append(String.format("\"%s\" ", _shortHelp));
        stringRepresentationBuilder.append(String.format("\"%s\" ", _fullHelp));

        return stringRepresentationBuilder.toString();
    }

}
