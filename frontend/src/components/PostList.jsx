// 게시글 목록을 보여주는 단순한 프레젠테이션 컴포넌트입니다.
export default function PostList({ posts, onSelect, onDelete }) {
  // 아직 글이 없다면 사용자에게 빈 상태 메시지를 보여줍니다.
  if (posts.length === 0) {
    return <p>아직 작성된 게시글이 없습니다.</p>;
  }

  return (
    <ul className="post-list">
      {posts.map((post) => (
        <li key={post.id} className="post-list__item">
          {/* 게시글 제목 영역을 버튼으로 만들어 클릭 시 상세 내용을 보여줄 수 있게 합니다. */}
          <button type="button" className="post-list__title" onClick={() => onSelect(post)}>
            <strong>{post.title}</strong>
            <span className="post-list__meta">
              {post.author} · {new Date(post.createdAt).toLocaleString()}
            </span>
          </button>
          {/* 삭제 버튼을 별도로 둬서 목록에서 바로 삭제할 수 있게 합니다. */}
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
