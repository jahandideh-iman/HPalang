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
	 * Enter a parse tree produced by {@link HPalangParser#var_name}.
	 * @param ctx the parse tree
	 */
	void enterVar_name(HPalangParser.Var_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#var_name}.
	 * @param ctx the parse tree
	 */
	void exitVar_name(HPalangParser.Var_nameContext ctx);
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
	 * Enter a parse tree produced by {@link HPalangParser#d_assignment}.
	 * @param ctx the parse tree
	 */
	void enterD_assignment(HPalangParser.D_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#d_assignment}.
	 * @param ctx the parse tree
	 */
	void exitD_assignment(HPalangParser.D_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#c_assignment}.
	 * @param ctx the parse tree
	 */
	void enterC_assignment(HPalangParser.C_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#c_assignment}.
	 * @param ctx the parse tree
	 */
	void exitC_assignment(HPalangParser.C_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#d_expr}.
	 * @param ctx the parse tree
	 */
	void enterD_expr(HPalangParser.D_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#d_expr}.
	 * @param ctx the parse tree
	 */
	void exitD_expr(HPalangParser.D_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#d_expr0}.
	 * @param ctx the parse tree
	 */
	void enterD_expr0(HPalangParser.D_expr0Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#d_expr0}.
	 * @param ctx the parse tree
	 */
	void exitD_expr0(HPalangParser.D_expr0Context ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#arithmetic_expr}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_expr(HPalangParser.Arithmetic_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#arithmetic_expr}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_expr(HPalangParser.Arithmetic_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#c_expr}.
	 * @param ctx the parse tree
	 */
	void enterC_expr(HPalangParser.C_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#c_expr}.
	 * @param ctx the parse tree
	 */
	void exitC_expr(HPalangParser.C_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#c_const}.
	 * @param ctx the parse tree
	 */
	void enterC_const(HPalangParser.C_constContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#c_const}.
	 * @param ctx the parse tree
	 */
	void exitC_const(HPalangParser.C_constContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#c_behavior}.
	 * @param ctx the parse tree
	 */
	void enterC_behavior(HPalangParser.C_behaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#c_behavior}.
	 * @param ctx the parse tree
	 */
	void exitC_behavior(HPalangParser.C_behaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#def_equ}.
	 * @param ctx the parse tree
	 */
	void enterDef_equ(HPalangParser.Def_equContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#def_equ}.
	 * @param ctx the parse tree
	 */
	void exitDef_equ(HPalangParser.Def_equContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#first_driv}.
	 * @param ctx the parse tree
	 */
	void enterFirst_driv(HPalangParser.First_drivContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#first_driv}.
	 * @param ctx the parse tree
	 */
	void exitFirst_driv(HPalangParser.First_drivContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#inv_expr}.
	 * @param ctx the parse tree
	 */
	void enterInv_expr(HPalangParser.Inv_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#inv_expr}.
	 * @param ctx the parse tree
	 */
	void exitInv_expr(HPalangParser.Inv_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#guard_expr}.
	 * @param ctx the parse tree
	 */
	void enterGuard_expr(HPalangParser.Guard_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#guard_expr}.
	 * @param ctx the parse tree
	 */
	void exitGuard_expr(HPalangParser.Guard_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_expr(HPalangParser.Boolean_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_expr(HPalangParser.Boolean_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#boolean_expr0}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_expr0(HPalangParser.Boolean_expr0Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#boolean_expr0}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_expr0(HPalangParser.Boolean_expr0Context ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterComparision_op(HPalangParser.Comparision_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitComparision_op(HPalangParser.Comparision_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#arithmetic_op}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_op(HPalangParser.Arithmetic_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#arithmetic_op}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_op(HPalangParser.Arithmetic_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#d_const}.
	 * @param ctx the parse tree
	 */
	void enterD_const(HPalangParser.D_constContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#d_const}.
	 * @param ctx the parse tree
	 */
	void exitD_const(HPalangParser.D_constContext ctx);
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