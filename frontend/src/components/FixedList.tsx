import { useFixedExtensions } from '../hooks/useFixedExtensions'
import React from 'react'

export const FixedList = () => {
  const { fixedList, loading, toggle } = useFixedExtensions()

  if (loading) return <p>로딩 중...</p>

  return (
    <div>
      <h2 className="text-xl font-bold mb-2">고정 확장자</h2>
      <ul className="space-y-1">
        {fixedList.map(item => (
          <li key={item.id}>
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                checked={item.checked}
                onChange={() => toggle(item.id)}
              />
              <span>{item.name}</span>
            </label>
          </li>
        ))}
      </ul>
    </div>
  )
}
