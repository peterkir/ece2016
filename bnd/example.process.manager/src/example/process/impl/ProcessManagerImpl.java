package example.process.impl;

import org.osgi.service.component.annotations.Component;

import example.process.ProcessManager;

import java.io.IOException;
import java.lang.ProcessBuilder;
import java.lang.ProcessHandle;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProcessManagerImpl implements ProcessManager {

    private final long currentUserUid = ProcessHandle.current().info().user().orElse("").hashCode();

    @Override
    public List<ProcessHandle> listUserProcesses() {
        return ProcessHandle.allProcesses()
            .filter(ProcessHandle::isAlive)
            .filter(ph -> ph.info().user().map(u -> u.hashCode() == currentUserUid).orElse(false))
            .sorted((p1, p2) -> Long.compare(p1.pid(), p2.pid()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ProcessHandle> listChildProcesses(long parentPid) {
        return ProcessHandle.of(parentPid)
            .map(ProcessHandle::children)
            .map(stream -> stream.filter(ProcessHandle::isAlive)
                .sorted((p1, p2) -> Long.compare(p1.pid(), p2.pid()))
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    @Override
    public Optional<ProcessHandle> startProcess(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            return Optional.of(process.toHandle());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean killProcess(long pid) {
        return ProcessHandle.of(pid)
            .filter(ProcessHandle::isAlive)
            .map(ProcessHandle::destroy)
            .orElse(false);
    }

}
