/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.Parameter;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;
import HPalang.SpaceEx.Core.Visitor;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Iman Jahandideh
 */
public class SpaceExToXMLConvertorVisitor implements Visitor
{
    private Element rootElement;
    private Document document;
    private Element currentComElem;
    
    public SpaceExToXMLConvertorVisitor()
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            document = docBuilder.newDocument();
            rootElement = document.createElement("sspaceex");
            document.appendChild(rootElement);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SpaceExToXMLConvertorVisitor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public void Visit(SpaceExModel model)
    {
        for(Component comp : model.GetComponents())
            comp.Accept(this);
    }
        
    @Override
    public void Visit(NetworkComponent component)
    {
        currentComElem = document.createElement("component");
        currentComElem.setAttribute("id", component.GetID());
        for(Parameter param : component.GetParameters())
            param.Accept(this);
        for(ComponentInstance ins : component.GetInstances())
            ins.Accept(this); 
        rootElement.appendChild(currentComElem);
    }
    
    @Override
    public void Visit(BaseComponent component)
    {
        currentComElem = document.createElement("component"); 
        currentComElem.setAttribute("id", component.GetID());
        for(Parameter param : component.GetParameters())
            param.Accept(this);
        for(Location loc : component.GetLocations())
            loc.Accept(this);
        for(HybridTransition tran : component.GetTransitions())
            tran.Accept(this);
           
        rootElement.appendChild(currentComElem);
    }
    
    @Override
    public void Visit(ComponentInstance instance)
    {
        Element instElem = document.createElement("bind");
        instElem.setAttribute("component", instance.GetType().GetID());
        instElem.setAttribute("as", instance.GetName());
        for(String formalParam : instance.GetBindedFromalParams())
        {
            Element mapElem = document.createElement("map");
            mapElem.setAttribute("key", formalParam);
            mapElem.appendChild(document.createTextNode(instance.GetBinding(formalParam)));
            instElem.appendChild(mapElem);
        }
        currentComElem.appendChild(instElem);
    }
    
    @Override
    public void Visit(RealParameter realParam)
    {
        Element paramElem = document.createElement("param");
        paramElem.setAttribute("name", realParam.GetName());
        paramElem.setAttribute("type", "real");
        paramElem.setAttribute("d1", "1");
        paramElem.setAttribute("d2", "1");
        paramElem.setAttribute("local", realParam.IsLocal().toString());
        paramElem.setAttribute("dynamics", "any");
        
        currentComElem.appendChild(paramElem);
    }

    @Override
    public void Visit(LabelParameter labelParam)
    {
        Element paramElem = document.createElement("param");
        paramElem.setAttribute("name", labelParam.GetName());
        paramElem.setAttribute("type", "label");
        paramElem.setAttribute("local", labelParam.IsLocal().toString());  
        currentComElem.appendChild(paramElem);
    }  
    
    @Override
    public void Visit(Location loc)
    {
        Element locElement = document.createElement("location");
        locElement.setAttribute("id", loc.GetId());
        locElement.setAttribute("name", loc.GetName());
        
        locElement.appendChild(CreateFlowElem(loc.GetFlows()));
        
        locElement.appendChild(CreateInvarientsElem(loc.GetInvarients()));
        
        currentComElem.appendChild(locElement);
    }

    @Override
    public void Visit(HybridTransition transition)
    {
        Element transElem = document.createElement("transition");
        transElem.setAttribute("source", transition.GetOrign().GetId());
        transElem.setAttribute("target", transition.GetDestination().GetId());
        
        transElem.appendChild(document.createElement("label"))
                .appendChild(document.createTextNode(transition.GetLabel().GetSyncLabel()));
        transElem.appendChild(document.createElement("guard"))
                .appendChild(document.createTextNode(String.join("&&", transition.GetLabel().GetGuards())));
        transElem.appendChild(document.createElement("assignment"))
                .appendChild(document.createTextNode(String.join("&&", transition.GetLabel().GetAssignments())));      
        currentComElem.appendChild(transElem);
    }

    private Element CreateFlowElem(Collection<Flow> flows)
    {
        Element flowElem = document.createElement("flow");
        
        List flowStr = flows.stream().map((Flow f) -> f.toString()).collect(Collectors.toList());
         
        flowElem.appendChild(document.createTextNode(String.join("&&", flowStr)));
        return flowElem;
    }
    
    private Element CreateInvarientsElem(Collection<Invarient> invs)
    {
        Element invElem = document.createElement("invariant");
        
        List invsStr = invs.stream().map((Invarient i) -> i.toString()).collect(Collectors.toList());
         
        invElem.appendChild(document.createTextNode(String.join("&&", invsStr)));
        return invElem;
    }
    
    public Document GetConvertedXML()
    {
        return document;
    }
}
