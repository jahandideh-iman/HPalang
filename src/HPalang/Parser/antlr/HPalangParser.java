// Generated from HPalang.txt by ANTLR 4.7
package HPalang.Parser.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HPalangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, END=11, ID=12, WS=13;
	public static final int
		RULE_model = 0, RULE_actor = 1, RULE_var_defs = 2, RULE_var_def = 3, RULE_type = 4, 
		RULE_method_defs = 5, RULE_method_def = 6, RULE_statements = 7, RULE_statement = 8, 
		RULE_send = 9, RULE_destination = 10, RULE_main = 11, RULE_message = 12;
	public static final String[] ruleNames = {
		"model", "actor", "var_defs", "var_def", "type", "method_defs", "method_def", 
		"statements", "statement", "send", "destination", "main", "message"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'actor'", "'{'", "'}'", "'real'", "'int'", "'('", "')'", "'.'", 
		"'self'", "'main'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "END", 
		"ID", "WS"
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

	@Override
	public String getGrammarFileName() { return "HPalang.txt"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HPalangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public MainContext main() {
			return getRuleContext(MainContext.class,0);
		}
		public List<ActorContext> actor() {
			return getRuleContexts(ActorContext.class);
		}
		public ActorContext actor(int i) {
			return getRuleContext(ActorContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitModel(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(26);
				actor();
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(31);
			main();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HPalangParser.ID, 0); }
		public Var_defsContext var_defs() {
			return getRuleContext(Var_defsContext.class,0);
		}
		public Method_defsContext method_defs() {
			return getRuleContext(Method_defsContext.class,0);
		}
		public ActorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterActor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitActor(this);
		}
	}

	public final ActorContext actor() throws RecognitionException {
		ActorContext _localctx = new ActorContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_actor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(T__0);
			setState(34);
			match(ID);
			setState(35);
			match(T__1);
			setState(36);
			var_defs();
			setState(37);
			method_defs();
			setState(38);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_defsContext extends ParserRuleContext {
		public List<Var_defContext> var_def() {
			return getRuleContexts(Var_defContext.class);
		}
		public Var_defContext var_def(int i) {
			return getRuleContext(Var_defContext.class,i);
		}
		public Var_defsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_defs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterVar_defs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitVar_defs(this);
		}
	}

	public final Var_defsContext var_defs() throws RecognitionException {
		Var_defsContext _localctx = new Var_defsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_defs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3 || _la==T__4) {
				{
				{
				setState(40);
				var_def();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_defContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(HPalangParser.ID, 0); }
		public TerminalNode END() { return getToken(HPalangParser.END, 0); }
		public Var_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterVar_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitVar_def(this);
		}
	}

	public final Var_defContext var_def() throws RecognitionException {
		Var_defContext _localctx = new Var_defContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_var_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			type();
			setState(47);
			match(ID);
			setState(48);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RealContext extends TypeContext {
		public RealContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterReal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitReal(this);
		}
	}
	public static class IntContext extends TypeContext {
		public IntContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitInt(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		try {
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				_localctx = new RealContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				match(T__3);
				}
				break;
			case T__4:
				_localctx = new IntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_defsContext extends ParserRuleContext {
		public List<Method_defContext> method_def() {
			return getRuleContexts(Method_defContext.class);
		}
		public Method_defContext method_def(int i) {
			return getRuleContext(Method_defContext.class,i);
		}
		public Method_defsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_defs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterMethod_defs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitMethod_defs(this);
		}
	}

	public final Method_defsContext method_defs() throws RecognitionException {
		Method_defsContext _localctx = new Method_defsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_method_defs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(54);
				method_def();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_defContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HPalangParser.ID, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public Method_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterMethod_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitMethod_def(this);
		}
	}

	public final Method_defContext method_def() throws RecognitionException {
		Method_defContext _localctx = new Method_defContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_method_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(ID);
			setState(61);
			match(T__5);
			setState(62);
			match(T__6);
			setState(63);
			match(T__1);
			setState(64);
			statements();
			setState(65);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> END() { return getTokens(HPalangParser.END); }
		public TerminalNode END(int i) {
			return getToken(HPalangParser.END, i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitStatements(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_statements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8 || _la==ID) {
				{
				{
				setState(67);
				statement();
				setState(68);
				match(END);
				}
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public SendContext send() {
			return getRuleContext(SendContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			send();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SendContext extends ParserRuleContext {
		public DestinationContext destination() {
			return getRuleContext(DestinationContext.class,0);
		}
		public MessageContext message() {
			return getRuleContext(MessageContext.class,0);
		}
		public SendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_send; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterSend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitSend(this);
		}
	}

	public final SendContext send() throws RecognitionException {
		SendContext _localctx = new SendContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_send);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			destination();
			setState(78);
			match(T__7);
			setState(79);
			message();
			setState(80);
			match(T__5);
			setState(81);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DestinationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HPalangParser.ID, 0); }
		public DestinationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_destination; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterDestination(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitDestination(this);
		}
	}

	public final DestinationContext destination() throws RecognitionException {
		DestinationContext _localctx = new DestinationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_destination);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_la = _input.LA(1);
			if ( !(_la==T__8 || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainContext extends ParserRuleContext {
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitMain(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_main);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(T__9);
			setState(86);
			match(T__1);
			setState(87);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(HPalangParser.ID, 0); }
		public MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).enterMessage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HPalangListener ) ((HPalangListener)listener).exitMessage(this);
		}
	}

	public final MessageContext message() throws RecognitionException {
		MessageContext _localctx = new MessageContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17^\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\3\2\6\2\36\n\2\r\2\16\2\37\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\4\7\4,\n\4\f\4\16\4/\13\4\3\5\3\5\3\5\3\5\3\6\3\6"+
		"\5\6\67\n\6\3\7\7\7:\n\7\f\7\16\7=\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\7\tI\n\t\f\t\16\tL\13\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\2\3\4\2\13\13\16\16\2U\2\35\3\2\2\2\4#\3\2\2\2\6-\3\2\2\2"+
		"\b\60\3\2\2\2\n\66\3\2\2\2\f;\3\2\2\2\16>\3\2\2\2\20J\3\2\2\2\22M\3\2"+
		"\2\2\24O\3\2\2\2\26U\3\2\2\2\30W\3\2\2\2\32[\3\2\2\2\34\36\5\4\3\2\35"+
		"\34\3\2\2\2\36\37\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\"\5\30"+
		"\r\2\"\3\3\2\2\2#$\7\3\2\2$%\7\16\2\2%&\7\4\2\2&\'\5\6\4\2\'(\5\f\7\2"+
		"()\7\5\2\2)\5\3\2\2\2*,\5\b\5\2+*\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2"+
		"\2.\7\3\2\2\2/-\3\2\2\2\60\61\5\n\6\2\61\62\7\16\2\2\62\63\7\r\2\2\63"+
		"\t\3\2\2\2\64\67\7\6\2\2\65\67\7\7\2\2\66\64\3\2\2\2\66\65\3\2\2\2\67"+
		"\13\3\2\2\28:\5\16\b\298\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\r\3\2"+
		"\2\2=;\3\2\2\2>?\7\16\2\2?@\7\b\2\2@A\7\t\2\2AB\7\4\2\2BC\5\20\t\2CD\7"+
		"\5\2\2D\17\3\2\2\2EF\5\22\n\2FG\7\r\2\2GI\3\2\2\2HE\3\2\2\2IL\3\2\2\2"+
		"JH\3\2\2\2JK\3\2\2\2K\21\3\2\2\2LJ\3\2\2\2MN\5\24\13\2N\23\3\2\2\2OP\5"+
		"\26\f\2PQ\7\n\2\2QR\5\32\16\2RS\7\b\2\2ST\7\t\2\2T\25\3\2\2\2UV\t\2\2"+
		"\2V\27\3\2\2\2WX\7\f\2\2XY\7\4\2\2YZ\7\5\2\2Z\31\3\2\2\2[\\\7\16\2\2\\"+
		"\33\3\2\2\2\7\37-\66;J";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}