package tfvccheckout;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.WritingAccessProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public final class WriteAccessProviderAction extends WritingAccessProvider {

    @NotNull
    @Override
    public Collection<VirtualFile> requestWriting(final VirtualFile... files) {
        if(!files[0].isWritable()) {
            try{
                Runtime rt = Runtime.getRuntime();
                String teamExplorerTfFilePath = "\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\TeamExplorer\\Common7\\IDE\\CommonExtensions\\Microsoft\\TeamFoundation\\Team Explorer\\TF.exe\"";
                String command = String.format("cmd.exe /c %s checkout %s", teamExplorerTfFilePath, files[0].getPath());
                Process pr = rt.exec(command);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean isPotentiallyWritable(@NotNull final VirtualFile file) {
        return true;
    }
}
