/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.Core.Statements.DelayStatement;
import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.Core.Invarient;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayLocation extends StatementLocation
{
    private DelayStatement statement;
    private String delayVar;

    public DelayLocation(DelayStatement statement, String name, ActorModelData actorData)
    {
        super(name+"_delay", actorData);
        this.statement = statement;
        this.delayVar = actorData.DelayVar();
        String invarient = actorData.DelayInvarient(statement.GetDelay());
        String flow = actorData.DelayFlow();

        loc.AddInvarient(new Invarient(invarient));
        loc.AddFlow(new Flow(flow));
    }

    @Override
    public void ProcessInLabel(HybridLabel label)
    {
        label.AddAssignment(actorData.ResetFor(actorData.DelayVar(), 0));
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        label.AddGuard(delayVar + " == " + String.valueOf(statement.GetDelay()));
    }
}
