/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.CommunicationType;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActor;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class HRToHAExample2
{
    
    static final String A__b_ins_param = "bIns";
    static final String A__c = "c";
    static final String A__X1_handler = "X1";

    
    static final String B__a_ins_param = "aIns";
    static final String B__Y1_handler = "Y1";
    
    public static ModelDefinition Create()
    {
        ModelDefinition model = new ModelDefinition();

        SoftwareActorType AType = new SoftwareActorType("A");
        SoftwareActorType BType = new SoftwareActorType("B");

        FillSkeletonForAType(AType, BType);
        FillSkeletonForBType(BType, AType);

        FillFleshForAType(AType);
        FillFleshForBType(BType);

        SoftwareActor a = new SoftwareActor("a", AType, 1);
        SoftwareActor b = new SoftwareActor("b", BType, 1);

        //a.SetInitialValue((IntegerVariable)a.Type().FindVariable(A__c), 10);
        BindInstance(a, A__b_ins_param, b, CommunicationType.Wire);
        BindInstance(b, B__a_ins_param, a, CommunicationType.Wire);
        
        MainBlock mainBlock = new MainBlock();
        
        mainBlock.AddSendStatement(CreateDirectSendStatement(b, B__Y1_handler));

        model.AddActor(a);
        model.AddActor(b);
        model.SetMainBlock(mainBlock);
        
        return model;
    }

    private static void FillSkeletonForAType(SoftwareActorType AType, SoftwareActorType BType)
    {
        AddInstanceParameter(AType, A__b_ins_param, BType);
        AddFloatVariable(AType, A__c);
        
        
        MessageHandler X1 = AddControlMessageHandler(AType, A__X1_handler);
    }

    private static void FillSkeletonForBType(SoftwareActorType BType, SoftwareActorType AType)
    {
        AddInstanceParameter(BType, B__a_ins_param, AType);
        AddControlMessageHandler(BType, B__Y1_handler);
    }

    private static void FillFleshForAType(SoftwareActorType AType)
    {
        InstanceParameter bIns = AType.FindInstanceParameter(A__b_ins_param);
        Variable c = AType.FindVariable(A__c);
        
        MessageHandler X1 = AType.FindMessageHandler(A__X1_handler);
        
        X1.AddStatement(new DelayStatement(1f));
        X1.AddStatement(new AssignmentStatement(c, 
                CreateBinaryExpression(new VariableExpression(c),"-", new ConstantDiscreteExpression(1))));
        //X1.AddStatement(CreateSendStatement(bIns, B__Y1_handler));

        X1.AddStatement(new IfStatement(
                CreateGreaterExpression(new VariableExpression(c), new ConstantDiscreteExpression(0)), 
                Statement.StatementsFrom(CreateSendStatement(bIns, B__Y1_handler)), 
                Statement.EmptyStatements()));
    }

    private static void FillFleshForBType(SoftwareActorType BType)
    {
        InstanceParameter aIns = BType.FindInstanceParameter(B__a_ins_param);
        MessageHandler Y1 = BType.FindMessageHandler(B__Y1_handler);
        
        Y1.AddStatement(CreateSendStatement(aIns, A__X1_handler));
    }
}
