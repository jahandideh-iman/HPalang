/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableArgument;
import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class SendLocation extends UrgentLocation
{
    private final SendStatement statement;

    public SendLocation(SendStatement statement, String name, ActorModelData actorData)
    {
        super(name+"_send", actorData);
        this.statement = statement;
        //this.sendLabel = actorData.GetSendLabelFor(statement).GetLabel();
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        super.ProcessOutLabel(label);
        //label.SetSyncLabel(actorData.ReceiverBufferLabel(statement));
        label.AddAssignment(actorData.SetMessageBufferMessageAssignment(statement));
        label.AddGuard(actorData.ReceiverMessageBufferEmptyGuard(statement));
        label.AddAssignment(actorData.SetMessageBufferFullAssignment(statement));

        for (String parameterAssignemtns : actorData.BufferParameterAssignmentsFor(statement)) {
            label.AddAssignment(parameterAssignemtns);
        }
 
    }

//    @Override
//    public void ProcessInLabel(HybridLabel label)
//    {
//        super.ProcessInLabel(label); 
//        label.AddAssignment(actorData.SetMessageBufferMessageAssignment(statement));
//
//        for (String parameterAssignemtns : actorData.BufferParameterAssignmentsFor(statement)) {
//            label.AddAssignment(parameterAssignemtns);
//        }
//    }
    
    

}
