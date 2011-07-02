package org.naga.project.yahoo.dev;

public class ElectricPowerUsageInformation {

  public ElectricPowerUsageInformation() {
    this.tokyo = null;
    this.tohoku = null;
    this.kansai = null;

    // Default is tokyo area
    this.useTokyo = true;
    this.useTohoku = false;
    this.useKansai = false;
  }

  public ElectricPowerUsage tokyo;

  public ElectricPowerUsage tohoku;

  public ElectricPowerUsage kansai;

  public boolean useTokyo;
  public boolean useTohoku;
  public boolean useKansai;

}
