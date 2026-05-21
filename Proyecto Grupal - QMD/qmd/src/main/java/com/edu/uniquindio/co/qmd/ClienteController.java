package com.edu.uniquindio.co.qmd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepo;

    public ClienteController(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepo.findAll();
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        return clienteRepo.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setActivo(clienteActualizado.getActivo());
            return ResponseEntity.ok(clienteRepo.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteRepo.existsById(id)) {
            clienteRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}