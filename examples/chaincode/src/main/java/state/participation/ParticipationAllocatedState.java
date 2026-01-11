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

public class ParticipationAllocatedState extends ParticipationState {
  //========== Constructor ===========
  public ParticipationAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrParticipation(Participation object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //EconomicEvent
    if(!StubHelper.exists(ctx, object.getEconomicEventId_EconomicEvent_Participation()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicEventId_EconomicEvent_Participation() + " does not exist");

    //Retrieve Master (economicEvent_EconomicEvent_Participation)
    EconomicEvent economicEvent_EconomicEvent_Participation = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_Participation());

    //EconomicAgent
    if(!StubHelper.exists(ctx, object.getEconomicAgentId_EconomicAgent_Participation()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicAgentId_EconomicAgent_Participation() + " does not exist");

    //Retrieve Master (economicAgent_EconomicAgent_Participation)
    EconomicAgent economicAgent_EconomicAgent_Participation = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Participation());


    //Changes the state of the current Participation (object)
    ParticipationUndirectedState newState = new ParticipationUndirectedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    economicEvent_EconomicEvent_Participation.getCurrentState().handle_EVcrParticipation(economicEvent_EconomicEvent_Participation, ctx);

    //EconomicAgent
    economicAgent_EconomicAgent_Participation.getCurrentState().handle_EVcrParticipation(economicAgent_EconomicAgent_Participation, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
