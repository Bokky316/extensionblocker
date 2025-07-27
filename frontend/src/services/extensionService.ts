import axios from 'axios'
import type { FixedExtension, CustomExtension } from '@/types'

/**
 * VITE_API_BASE_URL 예시: http://15.165.114.113/api
 * → axios 요청에서는 /api 중복 제거 필요
 */
const API = import.meta.env.VITE_API_BASE_URL

// 고정 확장자 목록 조회
export const getFixedExtensions = async (): Promise<FixedExtension[]> => {
  const res = await axios.get(`${API}/fixed`)
  if (!res || !res.data) throw new Error('응답 없음')
  const data = res.data.data ?? res.data
  if (!Array.isArray(data)) throw new Error('고정 확장자 응답 형식 오류')
  return data
}

// 고정 확장자 체크 상태 토글
export const toggleFixedExtension = async (id: number): Promise<FixedExtension> => {
  const res = await axios.put(`${API}/fixed/${id}`)
  if (!res || !res.data) throw new Error('응답 없음')

  const data = res.data.data ?? res.data  // ✅ 핵심 처리
  if (!data || typeof data.id !== 'number') {
    console.error('[toggleFixedExtension] 응답 이상:', res.data)
    throw new Error('응답이 null이거나 구조가 다릅니다')
  }

  return data
}

// 커스텀 확장자 목록 조회
export const getCustomExtensions = async (): Promise<CustomExtension[]> => {
  const res = await axios.get(`${API}/extensions/custom`)
  if (!res || !res.data) throw new Error('응답 없음')
  const data = res.data.data ?? res.data
  if (!Array.isArray(data)) throw new Error('커스텀 확장자 응답 형식 오류')
  return data
}

// 커스텀 확장자 등록
export const addCustomExtension = async (name: string): Promise<CustomExtension> => {
  const res = await axios.post(`${API}/extensions/custom`, { name })
  if (!res || !res.data) throw new Error('응답 없음')
  const data = res.data.data ?? res.data
  if (!data || typeof data.id !== 'number') {
    console.error('[addCustomExtension] 응답 이상:', res.data)
    throw new Error('응답이 null이거나 구조가 다릅니다')
  }
  return data
}

// 커스텀 확장자 삭제
export const deleteCustomExtension = async (id: number): Promise<void> => {
  await axios.delete(`${API}/extensions/custom/${id}`)
}
