package at.poquito.assetmanager;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssetPathFilter implements Comparable<AssetPathFilter> {
	private static final String NOT_A_DIRECTORY = "[^/]+";

	private static final String ANY_DIRECTORY = "(.*/|)";

	private static final String SUBDIRECTORY_REQUIRED = "(/.*/|/)";

	private static final String SUBDIRECTORY_OPTIONAL = "(/.*|$)";

	private static final Pattern EXPR_PATTERN = Pattern.compile("/?\\*\\*/?|\\*|/|[^*/]+");

	private Pattern pattern;

	private String expression;

	public AssetPathFilter(String expression) {
		this.expression = expression;
		pattern = buildPattern(expression);
	}

	public AssetPathFilter(AssetPath path) {
		if (path.isRoot()) {
			this.expression = "/**";
		} else {
			this.expression = path.getPath().concat("/**");
		}
		pattern = buildPattern(expression);
	}

	public static final Pattern buildPattern(String expression) {
		return Pattern.compile(createPattern(expression));
	}

	static String createPattern(String expression) {
		StringBuilder builder = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(expression, ", ");
		while (tokenizer.hasMoreElements()) {
			if (builder.length() > 0) {
				builder.append("|");
			}
			convertToRegExp(builder, tokenizer.nextToken());
		}
		return builder.toString();
	}

	static void convertToRegExp(StringBuilder builder, String nextToken) {
		Matcher matcher = EXPR_PATTERN.matcher(nextToken);
		while (matcher.find()) {
			String group = matcher.group();
			if ("*".equals(group)) {
				builder.append(NOT_A_DIRECTORY);
			} else if ("/**/".equals(group)) {
				builder.append(SUBDIRECTORY_REQUIRED);
			} else if ("/**".equals(group)) {
				builder.append(SUBDIRECTORY_OPTIONAL);
			} else if ("**/".equals(group)) {
				builder.append(ANY_DIRECTORY);
			} else {
				builder.append(Pattern.quote(group));
			}
		}
	}

	public String expression() {
		return expression;
	}

	public boolean test(String string) {
		return pattern.matcher(string).matches();
	}

	public boolean test(AssetPathFilter filter) {
		return pattern.matcher(filter.expression).matches();
	}

	@Override
	public int compareTo(AssetPathFilter o) {
		if (expression.equals(o.expression())) {
			return 0;
		}
		if (test(o)) {
			return 1;
		}
		return -1;
	}

}
