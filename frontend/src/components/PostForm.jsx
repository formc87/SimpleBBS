import { useEffect, useState } from 'react';

// 폼의 기본값을 한 곳에서 관리하기 위한 객체입니다.
const initialState = {
  title: '',
  content: '',
  author: ''
};

// 게시글 작성/수정에 사용되는 폼 컴포넌트입니다.
export default function PostForm({ initialValue, onSubmit, onCancel }) {
  // 입력 필드의 현재 값을 저장합니다.
  const [form, setForm] = useState(initialState);

  // 수정 모드일 때는 기존 값을 폼에 채워 넣고, 새 글 작성일 때는 초기 상태로 되돌립니다.
  useEffect(() => {
    if (initialValue) {
      setForm({
        title: initialValue.title,
        content: initialValue.content,
        author: initialValue.author
      });
    } else {
      setForm(initialState);
    }
  }, [initialValue]);

  // 입력값이 바뀔 때마다 해당 필드만 갱신합니다.
  function handleChange(event) {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  // 폼 제출 시 페이지 새로고침을 막고 상위 컴포넌트로 데이터를 올립니다.
  function handleSubmit(event) {
    event.preventDefault();
    onSubmit(form);
  }

  return (
    <form className="post-form" onSubmit={handleSubmit}>
      <label className="post-form__field">
        <span>제목</span>
        <input
          type="text"
          name="title"
          value={form.title}
          onChange={handleChange}
          maxLength={150}
          required
        />
      </label>

      <label className="post-form__field">
        <span>작성자</span>
        <input
          type="text"
          name="author"
          value={form.author}
          onChange={handleChange}
          maxLength={50}
          required
        />
      </label>

      <label className="post-form__field">
        <span>내용</span>
        <textarea
          name="content"
          value={form.content}
          onChange={handleChange}
          rows={6}
          required
        />
      </label>

      <div className="post-form__actions">
        <button type="submit" className="post-form__submit">
          {initialValue ? '수정하기' : '작성하기'}
        </button>
        {onCancel && (
          <button type="button" className="post-form__cancel" onClick={onCancel}>
            취소
          </button>
        )}
      </div>
    </form>
  );
}
