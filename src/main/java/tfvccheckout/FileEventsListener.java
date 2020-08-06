package tfvccheckout;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileEventsListener implements BulkFileListener {
    // TODO: to be used from a setting input
    private final String teamExplorerTfFilePath = "\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\TeamExplorer\\Common7\\IDE\\CommonExtensions\\Microsoft\\TeamFoundation\\Team Explorer\\TF.exe\"";

    @Override
    public void before(@NotNull List<? extends VFileEvent> events) {
        for (VFileEvent event : events) {
            if(event instanceof VFileMoveEvent) {
                System.out.println("Something");
                //MoveEvent((VFileMoveEvent)event);
                continue;
            }

            if(event instanceof VFilePropertyChangeEvent) {
                System.out.println("Something");
                //PropertyChangeEvent((VFilePropertyChangeEvent)event);
                continue;
            }
        }
    }

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        for (VFileEvent event : events) {
            if(event instanceof VFileCreateEvent) {
                CreateEvent((VFileCreateEvent) event);
                continue;
            }

            if(event instanceof VFileDeleteEvent) {
                DeleteEvent((VFileDeleteEvent)event);
                System.out.println("Something");
                continue;
            }
        }
    }

    private void CreateEvent(VFileCreateEvent event) {
        try{
            VirtualFile file = event.getFile();
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("cmd.exe /c %s add %s", teamExplorerTfFilePath, file.getPath());
            Process pr = runtime.exec(command);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void DeleteEvent(VFileDeleteEvent event) {
        try{
            VirtualFile file = event.getFile();
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("cmd.exe /c %s undo %s", teamExplorerTfFilePath, file.getPath());
            runtime.exec(command);
            command = String.format("cmd.exe /c %s delete %s", teamExplorerTfFilePath, file.getPath());
            runtime.exec(command);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void MoveEvent(VFileMoveEvent event) {
        try {
            VirtualFile file = event.getFile();
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("cmd.exe /c %s rename %s %s", teamExplorerTfFilePath, event.getOldPath(), event.getNewPath());
            runtime.exec(command);
            System.out.println(command);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void PropertyChangeEvent(VFilePropertyChangeEvent event) {
        if(event.isRename()) {
            try{
                VirtualFile file = event.getFile();
                Runtime runtime = Runtime.getRuntime();
                String command = String.format("cmd.exe /c %s rename %s %s", teamExplorerTfFilePath, event.getOldPath(), event.getNewPath());
                runtime.exec(command);
                System.out.println(command);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
