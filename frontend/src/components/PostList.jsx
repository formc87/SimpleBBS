export default function PostList({ posts, onSelect, onDelete }) {
  if (posts.length === 0) {
    return <p>아직 작성된 게시글이 없습니다.</p>;
  }

  return (
    <ul className="post-list">
      {posts.map((post) => (
        <li key={post.id} className="post-list__item">
          <button type="button" className="post-list__title" onClick={() => onSelect(post)}>
            <strong>{post.title}</strong>
            <span className="post-list__meta">
              {post.author} · {new Date(post.createdAt).toLocaleString()}
            </span>
          </button>
          <button
            type="button"
            className="post-list__delete"
            onClick={() => onDelete(post.id)}
          >
            삭제
          </button>
        </li>
      ))}
    </ul>
  );
}
