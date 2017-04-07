// Generated from HPalang.txt by ANTLR 4.7
package HPalang.Parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HPalangParser}.
 */
public interface HPalangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HPalangParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(HPalangParser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(HPalangParser.ModelContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#actor}.
	 * @param ctx the parse tree
	 */
	void enterActor(HPalangParser.ActorContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#actor}.
	 * @param ctx the parse tree
	 */
	void exitActor(HPalangParser.ActorContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#var_defs}.
	 * @param ctx the parse tree
	 */
	void enterVar_defs(HPalangParser.Var_defsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#var_defs}.
	 * @param ctx the parse tree
	 */
	void exitVar_defs(HPalangParser.Var_defsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterVar_def(HPalangParser.Var_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitVar_def(HPalangParser.Var_defContext ctx);
	/**
	 * Enter a parse tree produced by the {@code real}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterReal(HPalangParser.RealContext ctx);
	/**
	 * Exit a parse tree produced by the {@code real}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitReal(HPalangParser.RealContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterInt(HPalangParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitInt(HPalangParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#method_defs}.
	 * @param ctx the parse tree
	 */
	void enterMethod_defs(HPalangParser.Method_defsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#method_defs}.
	 * @param ctx the parse tree
	 */
	void exitMethod_defs(HPalangParser.Method_defsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#method_def}.
	 * @param ctx the parse tree
	 */
	void enterMethod_def(HPalangParser.Method_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#method_def}.
	 * @param ctx the parse tree
	 */
	void exitMethod_def(HPalangParser.Method_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(HPalangParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(HPalangParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(HPalangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(HPalangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#send}.
	 * @param ctx the parse tree
	 */
	void enterSend(HPalangParser.SendContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#send}.
	 * @param ctx the parse tree
	 */
	void exitSend(HPalangParser.SendContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#destination}.
	 * @param ctx the parse tree
	 */
	void enterDestination(HPalangParser.DestinationContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#destination}.
	 * @param ctx the parse tree
	 */
	void exitDestination(HPalangParser.DestinationContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(HPalangParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(HPalangParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#message}.
	 * @param ctx the parse tree
	 */
	void enterMessage(HPalangParser.MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#message}.
	 * @param ctx the parse tree
	 */
	void exitMessage(HPalangParser.MessageContext ctx);
}