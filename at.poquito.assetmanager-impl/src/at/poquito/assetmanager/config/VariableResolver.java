package at.poquito.assetmanager.config;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableResolver {
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("(\\$(\\$)?\\{([^\\}]+)\\})");

	private Properties variables;

	public VariableResolver() {
		this(System.getProperties());
	}

	public VariableResolver(Properties variables) {
		this.variables = variables;
	}

	public String resolve(String value) {
		StringBuffer buffer = new StringBuffer();
		Matcher matcher = VARIABLE_PATTERN.matcher(value);
		while (matcher.find()) {
			replaceVariable(buffer, matcher);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	private void replaceVariable(StringBuffer buffer, Matcher matcher) {
		if (matcher.group(2) == null) {
			appendReplacement(buffer, matcher);
		} else {
			appendEscapedExpression(buffer, matcher);
		}
	}

	private void appendEscapedExpression(StringBuffer buffer, Matcher matcher) {
		matcher.appendReplacement(buffer, Matcher.quoteReplacement("${"));
		buffer.append(matcher.group(3));
		buffer.append("}");
	}

	private void appendReplacement(StringBuffer buffer, Matcher matcher) {
		String replacement = variables.getProperty(matcher.group(3), matcher.group(1));
		matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
	}
}
