package com.baozistore.controller;

import com.baozistore.model.Produto;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto novoProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto dadosAtualizados) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(dadosAtualizados.getNome());
            produto.setPreco(dadosAtualizados.getPreco());
            produto.setEstoque(dadosAtualizados.getEstoque());
            Produto produtoAtualizado = produtoRepository.save(produto);
            return ResponseEntity.ok(produtoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
