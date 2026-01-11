package state.dependentView;

import entity.DependentView;
import entity.Participation;
import entity.Decrement;
import entity.StockFlow;
import entity.Increment;
import entity.EconomicAgent;
import entity.Duality;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class DependentViewEndedState extends DependentViewState {
  //========== Constructor ===========
  public DependentViewEndedState() {
    super("ended", StateType.FINAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---

  //--- Ending Events ---
}
