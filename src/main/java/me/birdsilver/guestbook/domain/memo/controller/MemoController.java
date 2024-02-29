package me.birdsilver.guestbook.domain.memo.controller;

import me.birdsilver.guestbook.domain.memo.dto.AddMemoRequest;
import me.birdsilver.guestbook.domain.memo.dto.DeleteMemoRequest;
import me.birdsilver.guestbook.domain.memo.dto.UpdateMemoRequest;
import me.birdsilver.guestbook.domain.memo.entity.Memo;
import me.birdsilver.guestbook.domain.memo.service.MemoService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping
    public ResponseEntity<Memo> addMemo(@RequestBody AddMemoRequest request) {
        if (request.getContent() == null || request.getContent().isEmpty()) {
            request.setContent(" ");
        }
        Memo savedMemo = memoService.save(request);
        return new ResponseEntity<>(savedMemo, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Memo> getAllMemos() {
        return memoService.findAll();
    }


    // 메모 수정 페이지
    @GetMapping("/update/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable long id) {
        Memo memo = memoService.findById(id);
        return ResponseEntity.ok(memo);
    }

    @GetMapping("/{recipientId}")
    public ResponseEntity<List<Memo>> getMemosByRecipient(@PathVariable Long recipientId) {

        List<Memo> memos = memoService.findByRecipient(recipientId);
        if (memos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(memos);
    }



    @PostMapping("/delete")
    public ResponseEntity<?> deleteMemo(@RequestBody DeleteMemoRequest request) {
        Long memoId = request.getMemoId();
        Memo memo = memoService.findById(memoId);

        if (memo.getPassword().equals(request.getPassword())) {
            memoService.delete(memoId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password mismatch");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMemo(@PathVariable long id, @RequestBody UpdateMemoRequest request) {
        Memo memo = memoService.findById(id);

        if (!request.getPassword().equals(memo.getPassword())) {
            // 비밀번호가 다를 경우 UNAUTHORIZED 상태와 에러 메시지를 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password mismatch");
        }

        Memo updatedMemo = memoService.update(id, request);
        return ResponseEntity.ok(updatedMemo);
    }
}
