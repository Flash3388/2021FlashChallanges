```
public static void main(String[] args) {
        File folder = new File("binaries");
        File[] binaries = folder.listFiles();

        for (int i = 0; i < binaries.length; ++i) {
            try {
                InputStream inputStream = new FileInputStream(binaries[i]);

                int j = 0;
                int b = inputStream.read();

                while (b != -1) {
                    if (b == INFECTED_MAGIC[j]) {
                        if (++j == INFECTED_MAGIC.length) {
                            System.out.format("%s: Infected\n", binaries[i].getName());
                            continue;
                        }
                    }
                    else {
                        j = 0;
                    }

                    b = inputStream.read();
                }

                System.out.format("%s: Clean\n", binaries[i].getName());
            }
            catch (Exception e) {
                continue;
            }
        }
    }
 ```
 
 ```
 69fe178f-26e7-43a9-aa7d-2b616b672dde_eventlogservice.dll: Clean
6bea57fb-8dfb-4177-9ae8-42e8b3529933_RuntimeDeviceInstall.dll: Clean
9EarsSurroundSound.dll: Clean
@AdvancedKeySettingsNotification.png: Clean
@AppHelpToast.png: Clean
@AudioToastIcon.png: Clean
@BackgroundAccessToastIcon.png: Clean
@bitlockertoastimage.png: Clean
@edptoastimage.png: Clean
@EnrollmentToastIcon.png: Clean
@language_notification_icon.png: Clean
@optionalfeatures.png: Infected
@StorageSenseToastIcon.png: Clean
@VpnToastIcon.png: Clean
@windows-hello-V4.1.gif: Clean
@WindowsHelloFaceToastIcon.png: Clean
@WindowsUpdateToastIcon.contrast-black.png: Clean
@WindowsUpdateToastIcon.contrast-white.png: Clean
@WindowsUpdateToastIcon.png: Clean
@WirelessDisplayToast.png: Clean
@WLOGO_48x48.png: Clean
aadauthhelper.dll: Infected
aadcloudap.dll: Clean
aadjcsp.dll: Clean
aadtb.dll: Clean
aadWamExtension.dll: Clean
AarSvc.dll: Clean
AboutSettingsHandlers.dll: Clean
AboveLockAppHost.dll: Clean
accessibilitycpl.dll: Clean
accountaccessor.dll: Clean
AccountsRt.dll: Clean
AcGenral.dll: Clean
AcLayers.dll: Clean
acledit.dll: Clean
aclui.dll: Clean
acmigration.dll: Clean
ACPBackgroundManagerPolicy.dll: Clean
acppage.dll: Clean
acproxy.dll: Clean
AcSpecfc.dll: Clean
ActionCenter.dll: Clean
ActionCenterCPL.dll: Clean
ActionQueue.dll: Infected
ActivationClient.dll: Clean
ActivationManager.dll: Clean
activeds.dll: Clean
activeds.tlb: Clean
ActiveHours.png: Clean
ActiveSyncCsp.dll: Clean
ActiveSyncProvider.dll: Clean
actxprxy.dll: Clean
AcWinRT.dll: Clean
AcXtrnal.dll: Clean
AdaptiveCards.dll: Clean
AddressParser.dll: Clean
adhapi.dll: Clean
adhsvc.dll: Clean
AdmTmpl.dll: Clean
adprovider.dll: Clean
adrclient.dll: Clean
adsldp.dll: Clean
adsldpc.dll: Clean
adsmsext.dll: Clean
adsnt.dll: Clean
adtschema.dll: Clean
AdvancedEmojiDS.dll: Clean
advapi32.dll: Clean
advapi32res.dll: Clean
advpack.dll: Clean
aeevts.dll: Clean
aeinv.dll: Clean
aepic.dll: Clean
agentactivationruntime.dll: Clean
agentactivationruntimestarter.exe: Clean
agentactivationruntimewindows.dll: Clean
AgentService.exe: Clean
aitstatic.exe: Clean
AJRouter.dll: Clean
alg.exe: Clean
altspace.dll: Clean
amcompat.tlb: Clean
amdtee_api.dll: Clean
amsi.dll: Clean
amsiproxy.dll: Clean
amstream.dll: Clean
Analog.Shell.Broker.dll: Clean
AnalogCommonProxyStub.dll: Clean
apds.dll: Clean
APHostClient.dll: Clean
APHostRes.dll: Clean
APHostService.dll: Clean
apisampling.dll: Clean
ApiSetHost.AppExecutionAlias.dll: Clean
apisetschema.dll: Clean
APMon.dll: Clean
AppContracts.dll: Clean
AppExtension.dll: Clean
apphelp.dll: Clean
Apphlpdm.dll: Clean
AppHostRegistrationVerifier.exe: Clean
aspell: Clean
dehtmldiff: Clean
dwz: Clean
ecryptfsd: Infected
PcdDxe.efi: Clean
```
