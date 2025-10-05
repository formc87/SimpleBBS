import { useEffect, useMemo, useState } from 'react';
import PostList from './components/PostList.jsx';
import PostForm from './components/PostForm.jsx';
import './App.css';

// 백엔드 API의 기본 경로입니다. 프록시 설정 덕분에 상대 경로로 호출할 수 있습니다.
const API_BASE = '/api/posts';

export default function App() {
  // 게시글 목록 상태입니다. 서버에서 받아온 데이터를 저장합니다.
  const [posts, setPosts] = useState([]);
  // 사용자가 선택한 게시글(수정 모드)을 저장합니다.
  const [selectedPost, setSelectedPost] = useState(null);
  // 서버 통신 중인지 여부를 표시합니다.
  const [loading, setLoading] = useState(false);
  // 오류 메시지를 사용자에게 보여주기 위한 상태입니다.
  const [error, setError] = useState('');

  // 게시글을 최신 순으로 정렬해서 보여주기 위한 메모이제이션 값입니다.
  const sortedPosts = useMemo(
    () => [...posts].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)),
    [posts]
  );

  // 컴포넌트가 처음 렌더링될 때 한 번만 게시글 목록을 불러옵니다.
  useEffect(() => {
    fetchPosts();
  }, []);

  // 전체 게시글을 API에서 읽어와 상태에 저장합니다.
  async function fetchPosts() {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(API_BASE);
      if (!response.ok) {
        throw new Error('게시글을 불러오지 못했습니다.');
      }
      const data = await response.json();
      setPosts(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  // 새 게시글을 등록할 때 호출됩니다.
  async function handleCreate(post) {
    setError('');
    try {
      const response = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(post)
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 등록하지 못했습니다.');
      }
      await fetchPosts();
      setSelectedPost(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // 선택한 게시글을 수정할 때 호출됩니다.
  async function handleUpdate(post) {
    if (!selectedPost) return;
    setError('');
    try {
      const response = await fetch(`${API_BASE}/${selectedPost.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(post)
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 수정하지 못했습니다.');
      }
      await fetchPosts();
      setSelectedPost(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // 게시글을 삭제할 때 호출됩니다.
  async function handleDelete(id) {
    setError('');
    try {
      const response = await fetch(`${API_BASE}/${id}`, {
        method: 'DELETE'
      });
      if (!response.ok) {
        const message = await extractError(response);
        throw new Error(message || '게시글을 삭제하지 못했습니다.');
      }
      // 삭제가 성공하면 로컬 상태에서도 해당 게시글을 제거합니다.
      setPosts((prev) => prev.filter((post) => post.id !== id));
      if (selectedPost?.id === id) {
        setSelectedPost(null);
      }
    } catch (err) {
      setError(err.message);
    }
  }

  // 서버에서 내려주는 에러 메시지를 최대한 친절하게 추려내는 보조 함수입니다.
  async function extractError(response) {
    try {
      const data = await response.json();
      if (data.errors) {
        return Object.values(data.errors).join(' ');
      }
      return data.message || '';
    } catch (error) {
      try {
        return await response.text();
      } catch (err) {
        return '';
      }
    }
  }

  // 목록에서 게시글을 클릭했을 때 선택 상태로 저장합니다.
  function handleSelect(post) {
    setSelectedPost(post);
  }

  // 수정 폼에서 취소 버튼을 누르면 선택 상태를 초기화합니다.
  function handleCancelEdit() {
    setSelectedPost(null);
  }

  return (
    <div className="app">
      <header className="app__header">
        <h1>SimpleBBS</h1>
        <p>Spring Boot와 React로 구현한 간단한 게시판입니다.</p>
      </header>

      <main className="app__content">
        <section className="app__section">
          <h2>게시글 목록</h2>
          {/* 로딩 상태나 오류 메시지를 사용자에게 바로 보여줍니다. */}
          {loading && <p>불러오는 중...</p>}
          {error && <p className="app__error">{error}</p>}
          <PostList posts={sortedPosts} onSelect={handleSelect} onDelete={handleDelete} />
        </section>

        <section className="app__section">
          <h2>{selectedPost ? '게시글 수정' : '새 게시글 작성'}</h2>
          <PostForm
            key={selectedPost?.id ?? 'new'}
            initialValue={selectedPost}
            onSubmit={selectedPost ? handleUpdate : handleCreate}
            onCancel={selectedPost ? handleCancelEdit : undefined}
          />
        </section>
      </main>
    </div>
  );
}
