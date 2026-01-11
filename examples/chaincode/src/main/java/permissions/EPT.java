package permissions;

import runtime.Permission;
import runtime.RuntimeEpt;

public class EPT {
  private static EPT instance = new EPT();
  private RuntimeEpt runtimeEpt;

  private EPT() {
    this.runtimeEpt = new RuntimeEpt();
  }

  public static RuntimeEpt getInstance() {
    return EPT.instance.runtimeEpt;
  }
}