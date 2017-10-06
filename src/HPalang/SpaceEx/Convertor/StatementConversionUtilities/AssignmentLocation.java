/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.Core.Statements.AssignmentStatement;
import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class AssignmentLocation extends UrgentLocation
{

    private final HPalang.Core.Statements.AssignmentStatement statement;

    public AssignmentLocation(AssignmentStatement statement, String name, ActorModelData actorData)
    {
        super(name + "_assignemnt", actorData);
        this.statement = statement;
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        super.ProcessOutLabel(label);
        label.AddAssignment(actorData.ResetFor(statement.Variable().Name(), statement.Expression().toString()));
    }
}
