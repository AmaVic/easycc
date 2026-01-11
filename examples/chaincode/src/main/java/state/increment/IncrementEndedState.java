package state.increment;

import entity.Increment;
import entity.Participation;
import entity.Ownership;
import entity.StockFlow;
import entity.EconomicResource;
import entity.Duality;
import entity.EconomicAgent;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class IncrementEndedState extends IncrementState {
  //========== Constructor ===========
  public IncrementEndedState() {
    super("ended", StateType.FINAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---

  //--- Ending Events ---
}
