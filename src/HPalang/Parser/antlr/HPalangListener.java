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
	 * Enter a parse tree produced by the {@code realT}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRealT(HPalangParser.RealTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realT}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRealT(HPalangParser.RealTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intT}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntT(HPalangParser.IntTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intT}
	 * labeled alternative in {@link HPalangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntT(HPalangParser.IntTContext ctx);
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
	 * Enter a parse tree produced by {@link HPalangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(HPalangParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(HPalangParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(HPalangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(HPalangParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#expr0}.
	 * @param ctx the parse tree
	 */
	void enterExpr0(HPalangParser.Expr0Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#expr0}.
	 * @param ctx the parse tree
	 */
	void exitExpr0(HPalangParser.Expr0Context ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterExpr1(HPalangParser.Expr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitExpr1(HPalangParser.Expr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(HPalangParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(HPalangParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterExpr3(HPalangParser.Expr3Context ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitExpr3(HPalangParser.Expr3Context ctx);
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
	 * Enter a parse tree produced by the {@code conjunction}
	 * labeled alternative in {@link HPalangParser#logical_op}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(HPalangParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conjunction}
	 * labeled alternative in {@link HPalangParser#logical_op}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(HPalangParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code disjuncition}
	 * labeled alternative in {@link HPalangParser#logical_op}.
	 * @param ctx the parse tree
	 */
	void enterDisjuncition(HPalangParser.DisjuncitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code disjuncition}
	 * labeled alternative in {@link HPalangParser#logical_op}.
	 * @param ctx the parse tree
	 */
	void exitDisjuncition(HPalangParser.DisjuncitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greater}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterGreater(HPalangParser.GreaterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greater}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitGreater(HPalangParser.GreaterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greaterEqual}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterGreaterEqual(HPalangParser.GreaterEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greaterEqual}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitGreaterEqual(HPalangParser.GreaterEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lesser}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterLesser(HPalangParser.LesserContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lesser}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitLesser(HPalangParser.LesserContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lesserEqual}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterLesserEqual(HPalangParser.LesserEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lesserEqual}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitLesserEqual(HPalangParser.LesserEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equality}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void enterEquality(HPalangParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equality}
	 * labeled alternative in {@link HPalangParser#comparision_op}.
	 * @param ctx the parse tree
	 */
	void exitEquality(HPalangParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plus}
	 * labeled alternative in {@link HPalangParser#arithmetic_op1}.
	 * @param ctx the parse tree
	 */
	void enterPlus(HPalangParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link HPalangParser#arithmetic_op1}.
	 * @param ctx the parse tree
	 */
	void exitPlus(HPalangParser.PlusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minus}
	 * labeled alternative in {@link HPalangParser#arithmetic_op1}.
	 * @param ctx the parse tree
	 */
	void enterMinus(HPalangParser.MinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minus}
	 * labeled alternative in {@link HPalangParser#arithmetic_op1}.
	 * @param ctx the parse tree
	 */
	void exitMinus(HPalangParser.MinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult}
	 * labeled alternative in {@link HPalangParser#arithmetic_op2}.
	 * @param ctx the parse tree
	 */
	void enterMult(HPalangParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link HPalangParser#arithmetic_op2}.
	 * @param ctx the parse tree
	 */
	void exitMult(HPalangParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code div}
	 * labeled alternative in {@link HPalangParser#arithmetic_op2}.
	 * @param ctx the parse tree
	 */
	void enterDiv(HPalangParser.DivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code div}
	 * labeled alternative in {@link HPalangParser#arithmetic_op2}.
	 * @param ctx the parse tree
	 */
	void exitDiv(HPalangParser.DivContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link HPalangParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(HPalangParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(HPalangParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#int_num}.
	 * @param ctx the parse tree
	 */
	void enterInt_num(HPalangParser.Int_numContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#int_num}.
	 * @param ctx the parse tree
	 */
	void exitInt_num(HPalangParser.Int_numContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#real_num}.
	 * @param ctx the parse tree
	 */
	void enterReal_num(HPalangParser.Real_numContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#real_num}.
	 * @param ctx the parse tree
	 */
	void exitReal_num(HPalangParser.Real_numContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#b_true}.
	 * @param ctx the parse tree
	 */
	void enterB_true(HPalangParser.B_trueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#b_true}.
	 * @param ctx the parse tree
	 */
	void exitB_true(HPalangParser.B_trueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HPalangParser#b_false}.
	 * @param ctx the parse tree
	 */
	void enterB_false(HPalangParser.B_falseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HPalangParser#b_false}.
	 * @param ctx the parse tree
	 */
	void exitB_false(HPalangParser.B_falseContext ctx);
}