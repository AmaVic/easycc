package state.participation;

import entity.Participation;
import entity.EconomicAgent;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class ParticipationUndirectedState extends ParticipationState {
  //========== Constructor ===========
  public ParticipationUndirectedState() {
    super("undirected", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVcrDecrement(Participation object, Context ctx) throws FailedEventHandlingException {

    //The Participation object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Participation currentLedgerObject = (Participation)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicEventId_EconomicEvent_Participation().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_Participation()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Participation().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Participation()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Participation (object)
    ParticipationProviderState newState = new ParticipationProviderState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_Participation = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_Participation());
    economicEvent_EconomicEvent_Participation.getCurrentState().handle_EVcrDecrement(economicEvent_EconomicEvent_Participation, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Participation = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Participation());
    economicAgent_EconomicAgent_Participation.getCurrentState().handle_EVcrDecrement(economicAgent_EconomicAgent_Participation, ctx);
  }
  @Override
  public void handle_EVcrIncrement(Participation object, Context ctx) throws FailedEventHandlingException {

    //The Participation object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Participation currentLedgerObject = (Participation)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicEventId_EconomicEvent_Participation().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_Participation()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Participation().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Participation()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Participation (object)
    ParticipationRecipientState newState = new ParticipationRecipientState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_Participation = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_Participation());
    economicEvent_EconomicEvent_Participation.getCurrentState().handle_EVcrIncrement(economicEvent_EconomicEvent_Participation, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Participation = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Participation());
    economicAgent_EconomicAgent_Participation.getCurrentState().handle_EVcrIncrement(economicAgent_EconomicAgent_Participation, ctx);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendParticipation(Participation object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("Participation (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current Participation (object)
    ParticipationArchivedState newState = new ParticipationArchivedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_Participation = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_Participation());
    economicEvent_EconomicEvent_Participation.getCurrentState().handle_EVendParticipation(economicEvent_EconomicEvent_Participation, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Participation = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Participation());
    economicAgent_EconomicAgent_Participation.getCurrentState().handle_EVendParticipation(economicAgent_EconomicAgent_Participation, ctx);
  }
}
