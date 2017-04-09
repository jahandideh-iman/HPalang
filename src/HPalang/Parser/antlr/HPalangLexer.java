// Generated from HPalang.txt by ANTLR 4.7
package HPalang.Parser.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HPalangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, TRUE=22, FALSE=23, REAL=24, INT=25, 
		END=26, ID=27, WS=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "TRUE", "FALSE", "REAL", "INT", "END", 
		"ID", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'actor'", "'{'", "'}'", "'real'", "'int'", "'('", "')'", "'.'", 
		"'self'", "'='", "'inv'", "'guard'", "'''", "'>'", "'>='", "'<'", "'<='", 
		"'=='", "'+'", "'-'", "'main'", "'true'", "'false'", null, null, "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "TRUE", "FALSE", 
		"REAL", "INT", "END", "ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public HPalangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "HPalang.txt"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u00aa\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\6\31\u008a\n\31\r\31\16"+
		"\31\u008b\3\31\3\31\6\31\u0090\n\31\r\31\16\31\u0091\3\32\6\32\u0095\n"+
		"\32\r\32\16\32\u0096\3\33\3\33\3\34\5\34\u009c\n\34\3\34\7\34\u009f\n"+
		"\34\f\34\16\34\u00a2\13\34\3\35\6\35\u00a5\n\35\r\35\16\35\u00a6\3\35"+
		"\3\35\2\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36\3\2\6\3\2\62;\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\2\u00ae"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3"+
		"\2\2\2\5A\3\2\2\2\7C\3\2\2\2\tE\3\2\2\2\13J\3\2\2\2\rN\3\2\2\2\17P\3\2"+
		"\2\2\21R\3\2\2\2\23T\3\2\2\2\25Y\3\2\2\2\27[\3\2\2\2\31_\3\2\2\2\33e\3"+
		"\2\2\2\35g\3\2\2\2\37i\3\2\2\2!l\3\2\2\2#n\3\2\2\2%q\3\2\2\2\'t\3\2\2"+
		"\2)v\3\2\2\2+x\3\2\2\2-}\3\2\2\2/\u0082\3\2\2\2\61\u0089\3\2\2\2\63\u0094"+
		"\3\2\2\2\65\u0098\3\2\2\2\67\u009b\3\2\2\29\u00a4\3\2\2\2;<\7c\2\2<=\7"+
		"e\2\2=>\7v\2\2>?\7q\2\2?@\7t\2\2@\4\3\2\2\2AB\7}\2\2B\6\3\2\2\2CD\7\177"+
		"\2\2D\b\3\2\2\2EF\7t\2\2FG\7g\2\2GH\7c\2\2HI\7n\2\2I\n\3\2\2\2JK\7k\2"+
		"\2KL\7p\2\2LM\7v\2\2M\f\3\2\2\2NO\7*\2\2O\16\3\2\2\2PQ\7+\2\2Q\20\3\2"+
		"\2\2RS\7\60\2\2S\22\3\2\2\2TU\7u\2\2UV\7g\2\2VW\7n\2\2WX\7h\2\2X\24\3"+
		"\2\2\2YZ\7?\2\2Z\26\3\2\2\2[\\\7k\2\2\\]\7p\2\2]^\7x\2\2^\30\3\2\2\2_"+
		"`\7i\2\2`a\7w\2\2ab\7c\2\2bc\7t\2\2cd\7f\2\2d\32\3\2\2\2ef\7)\2\2f\34"+
		"\3\2\2\2gh\7@\2\2h\36\3\2\2\2ij\7@\2\2jk\7?\2\2k \3\2\2\2lm\7>\2\2m\""+
		"\3\2\2\2no\7>\2\2op\7?\2\2p$\3\2\2\2qr\7?\2\2rs\7?\2\2s&\3\2\2\2tu\7-"+
		"\2\2u(\3\2\2\2vw\7/\2\2w*\3\2\2\2xy\7o\2\2yz\7c\2\2z{\7k\2\2{|\7p\2\2"+
		"|,\3\2\2\2}~\7v\2\2~\177\7t\2\2\177\u0080\7w\2\2\u0080\u0081\7g\2\2\u0081"+
		".\3\2\2\2\u0082\u0083\7h\2\2\u0083\u0084\7c\2\2\u0084\u0085\7n\2\2\u0085"+
		"\u0086\7u\2\2\u0086\u0087\7g\2\2\u0087\60\3\2\2\2\u0088\u008a\t\2\2\2"+
		"\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c"+
		"\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\13\2\2\2\u008e\u0090\t\2\2\2"+
		"\u008f\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092"+
		"\3\2\2\2\u0092\62\3\2\2\2\u0093\u0095\t\2\2\2\u0094\u0093\3\2\2\2\u0095"+
		"\u0096\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\64\3\2\2"+
		"\2\u0098\u0099\7=\2\2\u0099\66\3\2\2\2\u009a\u009c\t\3\2\2\u009b\u009a"+
		"\3\2\2\2\u009c\u00a0\3\2\2\2\u009d\u009f\t\4\2\2\u009e\u009d\3\2\2\2\u009f"+
		"\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a18\3\2\2\2"+
		"\u00a2\u00a0\3\2\2\2\u00a3\u00a5\t\5\2\2\u00a4\u00a3\3\2\2\2\u00a5\u00a6"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00a9\b\35\2\2\u00a9:\3\2\2\2\n\2\u008b\u0091\u0096\u009b\u009e\u00a0"+
		"\u00a6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}