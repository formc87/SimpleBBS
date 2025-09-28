import { useEffect, useState } from 'react';

const initialState = {
  title: '',
  content: '',
  author: ''
};

export default function PostForm({ initialValue, onSubmit, onCancel }) {
  const [form, setForm] = useState(initialState);

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

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

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
