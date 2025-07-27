// src/components/FixedList.tsx

import { useEffect } from 'react'
import { useFixedExtensions } from '@/hooks/useFixedExtensions'
import React from 'react'

export const FixedList = () => {
  const { fixedList, loading, error, toggle } = useFixedExtensions()

  // ✅ 상태 변경될 때마다 로그 출력
  useEffect(() => {
    console.log(
      '🟡 현재 fixedList 상태:',
      fixedList.map(f => ({ id: f.id, checked: f.checked }))
    )
  }, [fixedList])

  if (loading) return <p>로딩 중...</p>
  if (error) return <p>에러 발생: {error}</p>

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">고정 확장자</h2>
      <ul className="space-y-1">
        {fixedList.map(item => (
          <li key={`${item.id}-${item.checked}`}> {/* ✅ 강제 리렌더링 유도 */}
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                checked={!!item.checked} // ✅ boolean 강제 (uncontrolled 오류 방지)
                onChange={() => {
                  console.log('🟢 토글 클릭됨:', item.id)
                  toggle(item.id)
                }}
              />
              <span>{item.name}</span>
            </label>
          </li>
        ))}
      </ul>
    </div>
  )
}
